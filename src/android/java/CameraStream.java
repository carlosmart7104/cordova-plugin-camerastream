package org.carma;

import java.io.ByteArrayOutputStream;

import org.apache.cordova.*;
import org.json.JSONObject;
import org.json.JSONException;

import android.util.Log;
import android.util.Base64;
import android.media.Image;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.PictureCallback;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.SurfaceTexture;

class CameraStream {
	protected static final String TAG = "CameraStreamDebug";
	protected static Camera camera;
	protected static CallbackContext context;
	protected static JSONObject response;
	protected static JSONObject dataValues;

	private static byte[] lastImage;
	private static int fpscount;
	private static long lasttime = 0;
	public static float currentFPS = 0f;
	public byte[][] frameBuffers;
	public int fbCounter = 0;
	private boolean newImageNeeded;
	private long lastImageRequest = 0;
	public static int mDesiredWidth = 1280;
	public static int mDesiredHeight = 720;

	public static void init(CallbackContext callbackContext) {
		Log.i(TAG, "init() CameraStream.java");
		context = callbackContext;

		callbackContext.success();
	}

	public static void start(CallbackContext callbackContext) {
		Log.i(TAG, "start() CameraStream.java");
		context = callbackContext;

		camera = Camera.open();

		camera.startPreview();
		camera.setPreviewCallback(previewCallback);

		callbackContext.success();
	}

	public static void stop(CallbackContext callbackContext) {
		Log.i(TAG, "stop() CameraStream.java");
		context = callbackContext;

		camera.setPreviewCallback(null);
		camera.stopPreview();
		camera.release();
		camera = null;

		callbackContext.success();
	}

	public static void getFrame(CallbackContext callbackContext) {
		Log.i(TAG, "getFrame() CameraStream.java");
		context = callbackContext;

		// camera.takePicture(null, null, jpegCallback);

		try {
			if (lastImage != null) {
				int w = mDesiredWidth;
				int h = mDesiredHeight;
				int[] rgbArray = new int[w * h];

				decodeYUV420SP(rgbArray, lastImage, w, h);
				Bitmap bitmap = Bitmap.createBitmap(rgbArray, w, h, Config.ARGB_8888);

				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

				Matrix matrix = new Matrix();
				matrix.postRotate(90);

				Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

				rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 75, outputStream);

				String output = Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP);
				output = "data:image/jpeg;base64," + output;

				Log.i(TAG, "output:");
				Log.i(TAG, output);

				context.success(output);
			} else {
				Log.i(TAG, "lastImage is null");
				context.success();
			}
		} catch (Exception e) {
			handleException(e);
		}
	}

	/* private static PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] _data, Camera _camera) {
			Log.i(TAG, "onPictureTaken()");

			String base64jpeg = Base64.encodeToString(_data, Base64.NO_WRAP);
			Bitmap image = BitmapFactory.decodeByteArray(_data, 0, _data.length);

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
			byte[] byteArray = byteArrayOutputStream.toByteArray();
			String base64png = Base64.encodeToString(byteArray, Base64.NO_WRAP);

			Log.i(TAG, "base64jpeg");
			Log.i(TAG, base64jpeg);

			Log.i(TAG, "base64png");
			Log.i(TAG, base64png);

			try {
				response = new JSONObject();
				dataValues = new JSONObject();

				response.put("status", "OK");
				response.put("statusCode", 200);

				dataValues.put("jpeg", base64jpeg);
				dataValues.put("png", base64png);

				response.put("data", dataValues);
			} catch (Exception e) {
				handleException(e);
			}

			context.success(response.toString());
		}
	}; */

	private static PreviewCallback previewCallback = new PreviewCallback() {
		public void onPreviewFrame(byte[] data, Camera camera) {
			Log.i(TAG, "onPreviewFrame()");

			mDesiredWidth = camera.getParameters().getPreviewSize().width;
			mDesiredHeight = camera.getParameters().getPreviewSize().height;

			if (data != null) {
				Log.i(TAG, "data.length");
				Log.i(TAG, Integer.toString(data.length));

				lastImage = data;
			} else {
				Log.i(TAG, "data is null");
			}

			updateFps();
		}
	};

	private static void updateFps() {
		if (lasttime == 0) {
			lasttime = System.currentTimeMillis();
			fpscount = 0;
			currentFPS = 0;
		} else {
			long delay = System.currentTimeMillis() - lasttime;
			if (delay > 1000) {
				lasttime = System.currentTimeMillis();
				currentFPS = fpscount * 10000 / delay;
				currentFPS /= 10;
				fpscount = 0;
			}
		}
		fpscount++;
	}
	
	public static void decodeYUV420SP(int[] rgb, byte[] yuv420sp, int width, int height) {
	    final int frameSize = width * height;

	    for (int j = 0, yp = 0; j < height; j++) {
	        int uvp = frameSize + (j >> 1) * width, u = 0, v = 0;
	        for (int i = 0; i < width; i++, yp++) {
	            int y = (0xff & ((int) yuv420sp[yp])) - 16;
	            if (y < 0) y = 0;
	            if ((i & 1) == 0) {
	                v = (0xff & yuv420sp[uvp++]) - 128;
	                u = (0xff & yuv420sp[uvp++]) - 128;
	            }
	            int y1192 = 1192 * y;
	            int r = (y1192 + 1634 * v);
	            int g = (y1192 - 833 * v - 400 * u);
	            int b = (y1192 + 2066 * u);

	            if (r < 0) r = 0; else if (r > 262143) r = 262143;
	            if (g < 0) g = 0; else if (g > 262143) g = 262143;
	            if (b < 0) b = 0; else if (b > 262143) b = 262143;

	            rgb[yp] = 0xff000000 | ((r << 6) & 0xff0000) | ((g >> 2) & 0xff00) | ((b >> 10) & 0xff);
	        }
	    }
	}

	private static void handleError(String errorMsg) {
        try {
            Log.e(TAG, errorMsg);

            response = new JSONObject();
			response.put("status", "Error desconocido");
			response.put("statusCode", 600);
			response.put("message", errorMsg);

			context.error(response.toString());
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private static void handleException(Exception exception) {
        handleError(exception.toString());
    }
}