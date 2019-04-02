/**
 * Exporta modulo para interfaz cliente JS del plugin
 * @Module cordova/camerastream
 */
 module.exports = {
 	init: function(successCallback, errorCallback) {
 		cordova.exec(
 			successCallback,
 			errorCallback,
 			'CameraStreamPlugin',
 			'init',
 			[]
 		);
 	},
 	start(): function(successCallback, errorCallback) {
 		cordova.exec(
 			successCallback,
 			errorCallback,
 			'CameraStreamPlugin',
 			'start',
 			[]
 		);
 	},
 	stop(): function(successCallback, errorCallback) {
 		cordova.exec(
 			successCallback,
 			errorCallback,
 			'CameraStreamPlugin',
 			'stop',
 			[]
 		);
 	},
 	getFrame(): function(successCallback, errorCallback) {
 		cordova.exec(
 			successCallback,
 			errorCallback,
 			'CameraStreamPlugin',
 			'getFrame',
 			[]
 		);
 	},
 };