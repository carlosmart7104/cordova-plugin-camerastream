/**
 * Exporta modulo para interfaz cliente JS del plugin
 * @Module cordova/camerastream
 */
module.exports = {
 	start: function(successCallback, errorCallback) {
 		console.log('cordova.exec start is called');
 		cordova.exec(
 			successCallback,
 			errorCallback,
 			'CameraStreamPlugin',
 			'start',
 			[]
 		);
 	},
 	stop: function(successCallback, errorCallback) {
 		console.log('cordova.exec stop is called');
 		cordova.exec(
 			successCallback,
 			errorCallback,
 			'CameraStreamPlugin',
 			'stop',
 			[]
 		);
 	}
 };