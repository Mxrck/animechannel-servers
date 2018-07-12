# Anime Channel Servers Parser 

[![Build Status](https://travis-ci.com/Mxrck/animechannel-servers.svg?branch=dev)](https://travis-ci.com/Mxrck/animechannel-servers)

### === Actualización ===

Por comodidad y para el futuro, he añadído un submódulo llamado "Hoster" que se
encargará de parsear la URL directa del servidor (Mp4upload, Rapidvideo, Okru, Fire, ...)

De esta forma la tarea del IServer ahora es encontrar el enlace a su servidor
basándose en cómo devuelve las urls animeflv para después utilizar el parser correspondiente.

En este caso ya he ingresado algunos útiles listos para ser usados (aún pueden contener algunos errores)

##### Hosters

* ~~Mp4upload~~
* ~~Rapidvideo~~
* ~~Izanagi~~
* Mega
* ~~Okru~~
* ~~Mediafire~~
* ~~Streamango~~
* Openload
* ~~Yourupload~~
* ~~Maru~~
* Netu

##### IServers

* ~~Mp4upload~~
* ~~Rapidvideo~~
* ~~Izanagi~~
* Mega
* ~~Okru~~
* ~~Mediafire~~ (Ya no es usado por animeflv)
* ~~Streamango~~
* Openload
* ~~Yourupload~~
* ~~Maru~~
* Netu

De forma adicional, se han creado tests para cada uno de los servidores, tanto de la función de los hosters como de los servers
en caso de fallar alguno, primero intentar obtener nuevos enlaces por parte de animeflv

### === Fin Actualización ===

### Información

Debido a los recientes cambios implementados por AnimeFLV y con intención
de la mejora continua de Anime Channel, hacemos público el módulo de parseo
de servidores para que cualquier developer pueda apoyar a mantener actualizada
esta parte que es vital en el uso de la app.

### Detalles de uso

Anime Channel tiene un Factory que se encarga de crear una instancia de IServer
correcta dependiendo del capítulo que se esté mirando en ese momento

Este repositorio es para que crear las clases que serán llamadas por el Factory.

El flujo de la aplicación es el siguiente:

`App => Factory => IServer => isProcessable() ? => (si) => process() => getDirectURL() !== null ? => (si) => Play`

### Detalles técnicos

El paquete por defecto donde van los parsers es:

`mx.com.nitrostudio.animechannel.models.entities.servers.hosts`

Pueden basarse en el ejemplo Fire que actualmente se encarga de obtener la URL directa
al archivo en un servidor Mediafire

Actualmente AnimeFLV ya no maneja Fire, y si lo maneja ha cambiado su
implementación, por lo que el ejemplo ya no es usable para AnimeChannel

Pueden usar como base para sus hosts el siguiente snipped

```java
package mx.com.nitrostudio.animechannel.models.entities.servers.hosts;

import mx.com.nitrostudio.animechannel.models.entities.servers.GenericServer;
import mx.com.nitrostudio.animechannel.models.entities.servers.IServer;
import mx.com.nitrostudio.animechannel.models.webservice.ICallback;
import org.jetbrains.annotations.Nullable;

public class MockServer extends GenericServer implements IServer {

    @Nullable
    @Override
    public String getName() {
        return "Mock"; // Indispensable para ser procesado
    }

    @Override
    public boolean isProcessable() {
        return true; // Le dice a AnimeChannel que el servidor puede usarse
    }

    @Override
    public Thread process(final ICallback<String> callback) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (callback != null) callback.onStart();
                
                ...
                
                if (getDirectURL() != null && !getDirectURL().isEmpty() && callback != null) callback.onSuccess(getDirectURL());
                else
                if (callback != null) callback.onError("{ERROR MESSAGE}");
            }
        });
        thread.start();
        return thread;
    }
}
```

En el archivo `main.kt` se definen las urls tal y como llegarán a los IServer cuando
se llame al método `getURL()`, esto significa que si se encuentra
el servidor RV cuando en el IServer de RV se llame a `getURL()` nos devolverá un enlace
similar al siguiente: 

`https://s1.animeflv.net/redirector.php?top=/ver/49466/boruto-naruto-next-generations-63&server=rv&value=FSR7Q9KPP2`

Al ser este un repositorio de prueba y no tener el ServerFactory tendremos que prellenar
esta información manualmente, tal y como se muestra en el ejemplo de Mediafire

```kotlin
fun mediafireExample()
{
    var urlDemo = "http://www.mediafire.com/file/0djwmh1yr014od3/Colaboraci%C3%B3n+Top+Anime-DongHua+Openings+Invierno+2018.xlsx"
    var fireScraper = Fire()
    fireScraper.setUrl(urlDemo)
    fireScraper.process(null)?.join() // Join solo para fines de pruebas y esperar a que termine
    println(fireScraper.getDirectURL()) // Si es null, no puedo ser procesado, de lo contrario tendrá la url directa al archivo.
}
```

En caso de que el servidor no pueda ser parseado por cualquier razón, el método
`getDirectURL()` deberá devolver `null` (y llamar
                                         la función correspondiente del ICallback para notificar del error), en caso de que el servidor fuera
correctamente parseado y la URL al archivo correctamente obtenida, se deberá
devolver la URL directa al archivo en el método `getDirectURL()` (y llamar
la función correspondiente del ICallback para notificar del éxito) 

Evitamos la instalación de más librerías en la app, aunque se tienen disponibles 
las siguientes para utilizar:

* Kotlin (como lenguage si se deseara usar o Java)
* Jsoup
* Gson
* Jbro (Solo es un wrapper para HttpURLConnection que pueden ocupar de ser necesario)

Aún con esto si fuera necesario se podría aceptar el uso de cualquier librería
de ser necesario.

Cualquier problema o aclaración, podemos resolverla a través de los issues del proyecto ☕️