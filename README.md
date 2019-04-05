# Cordova Plugin CameraStream
Es un plugin de *cordova* que accede a la camara de forma nativa, permitiendo leer el stream de imágenes que se capturan en tiempo real.

El valor es retornado en formato jpg codificado en base64, de manera que es posible usarlo en la aplicación para signarlo a un elemento `<img>`, usarlo como variable, dibujarlo en un elemento `<canvas>` o incluso introducirlo a un sistema de procesamiento de imágenes o inteligencia artificial.

El plugin existe como un objeto global llamado "CameraStream", que cuenta con los métodos "start" y "stop", ambos métodos reciben como argumento un successCallback y un errorCallback, en ese orden.

Ejemplo:
```javascript
CameraStream.start(function(res) {
  // Este callback se ejecutará cada vez que haya un nuevo frame y este sea procesado correctamente.
  console.log('successCallback');
  document.getElementByID('image').src = res;
}, function(err) {
  // Este callback se ejecutará cada vez que haya un nuevo frame pero algo falle en el proceso.
  console.log('errorCallback');
  console.log(err);
});
```

```javascript
CameraStream.stop(function() {
  console.log('stop');
});
```

Para instalarlo solo es necesario desplazarse a la carpeta del proyecto, y ejecutar el comando de instalación:
```bash
cordova plugin add https://github.com/carlosmart7104/cordova-plugin-camerastream.git
```
A continuación se puede verificar que esté instalado usando el comando:
```bash
cordova plugin ls
```
Una vez instalado, está listo para ser usado en el proyecto
