package mx.com.nitrostudio.animechannel.models.entities.servers.hosts

import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.GenericServer
import mx.com.nitrostudio.animechannel.models.entities.servers.IServer
import mx.com.nitrostudio.animechannel.models.entities.servers.hoster.Fenix
import mx.com.nitrostudio.animechannel.models.webservice.ICallback
import kotlin.concurrent.thread

class FenixServer : GenericServer(), IServer {

    override fun getName(): String? = "Fenix"

    override fun isProcessable(): Boolean {
        return true
    }

    override fun process(callback: ICallback<String?>?): Thread? {
        return thread(start = true) {
            callback?.onStart()
            try {
                if (getDirectURL() == null) {
                    val fenix = Fenix()
                    runBlocking {
                        setDirectUrl(fenix.directLink(getURL() ?: "").await())
                    }
                }
            } catch (exception: Exception) {
                /* LOG */
                exception.printStackTrace()
            }
            if (getDirectURL() != null)
                callback?.onSuccess(getDirectURL())
            else
                callback?.onError("No se pudo procesar el enlace")
        }
    }
}