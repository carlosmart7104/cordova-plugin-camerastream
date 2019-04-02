/**
 * Exporta modulo para interfaz cliente JS del plugin
 * @Module cordova/camerastream
 */
module.exports = {
 	init: function(successCallback, errorCallback) {
 		console.log('cordova.exec init is called');
 		cordova.exec(
 			successCallback,
 			errorCallback,
 			'CameraStreamPlugin',
 			'init',
 			[]
 		);
 	},
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
 	},
 	getFrame: function(successCallback, errorCallback) {
 		console.log('cordova.exec getFrame is called');
 		cordova.exec(
 			successCallback,
 			errorCallback,
 			'CameraStreamPlugin',
 			'getFrame',
 			[]
 		);
 	},
 };