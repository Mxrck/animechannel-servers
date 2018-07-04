package mx.com.nitrostudio.animechannel.models.entities.servers.hosts

import kotlinx.coroutines.experimental.async
import mx.com.nitrostudio.animechannel.models.entities.servers.GenericServer
import mx.com.nitrostudio.animechannel.models.entities.servers.IServer
import mx.com.nitrostudio.animechannel.models.entities.servers.hoster.Mediafire
import mx.com.nitrostudio.animechannel.models.webservice.ICallback
import kotlin.concurrent.thread

class Fire : GenericServer(), IServer {

    override fun getName(): String? = "Fire"

    override fun isProcessable(): Boolean {
        return true
    }

    override fun process(callback: ICallback<String?>?): Thread? {
        return thread(start = true) {
            callback?.onStart()
            try {
                if (getDirectURL() == null)
                {
                    val mediafire = Mediafire()
                    async { setDirectUrl(mediafire.directLink(getURL() ?: "").await()) }
                }
            }
            catch (exception : Exception) { /* LOG */ }
            if (getDirectURL() != null)
                callback?.onSuccess(getDirectURL())
            else
                callback?.onError("No se pudo procesar el enlace")
        }
    }
}