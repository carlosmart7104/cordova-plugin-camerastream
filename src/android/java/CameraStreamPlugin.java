package org.carma;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import android.util.Log;

public class CameraStreamPlugin extends CordovaPlugin {
	protected static final String TAG = "CameraStreamPlugin";
	protected CallbackContext context;

    @Override
	public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
		context = callbackContext;
        boolean result = true;
        try {
            if (action.equals("start")) {
                // Log.i(TAG, "execute CameraStream.start()");
                CameraStream.start(context);

            } else if (action.equals("stop")) {
                // Log.i(TAG, "execute CameraStream.stop()");
                CameraStream.stop(context);

            }

        } catch (Exception e) {
            handleException(e);
            result = false;
        }
        return result;
	}

	/**
     * Handles an error while executing a plugin API method.
     * Calls the registered Javascript plugin error handler callback.
     *
     * @param errorMsg Error message to pass to the JS error handler
     */
    private void handleError(String errorMsg) {
        try {
            Log.e(TAG, errorMsg);
            context.error(errorMsg);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private void handleException(Exception exception) {
        handleError(exception.toString());
    }
}
