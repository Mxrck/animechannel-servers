package mx.com.nitrostudio.animechannel.models.entities.servers.hosts

import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.GenericServer
import mx.com.nitrostudio.animechannel.models.entities.servers.IServer
import mx.com.nitrostudio.animechannel.models.entities.servers.hoster.Streamango
import mx.com.nitrostudio.animechannel.models.webservice.ICallback
import mx.com.nitrostudio.animechannel.services.jbro.Jbro
import java.util.regex.Pattern
import kotlin.concurrent.thread

class StreamangoServer : GenericServer(), IServer {

    override fun getName(): String? = "Mango"

    override fun isProcessable(): Boolean {
        return true
    }

    override fun process(callback: ICallback<String?>?): Thread? {
        return thread(start = true){
            callback?.onStart()
            val http = Jbro.getInstance()
            val auxCache = http.isSkipCache
            http.isSkipCache = false
            if (getDirectURL() == null)
            {
                try {
                    val link = getURL() ?: ""
                    runBlocking {
                        setDirectUrl(Streamango().directLink(link).await())
                    }
                }
                catch (exception : Exception)
                {
                    /* LOG */
                    exception.printStackTrace()
                }
                finally {
                    http.isSkipCache = auxCache
                }
            }
            if (getDirectURL() != null)
                callback?.onSuccess(getDirectURL())
            else
                callback?.onError("No se pudo procesar el enlace")
        }
    }
}