package mx.com.nitrostudio.animechannel.models.entities.servers.hoster

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import mx.com.nitrostudio.animechannel.services.jbro.Jbro
import org.json.JSONObject

class Izanagi :  IHoster {

    override fun directLink(link: String): Deferred<String?> = async {
        var directLink : String? = null
        val http = Jbro.getInstance()
        val auxCache = http.isSkipCache
        http.isSkipCache = false
        try {
            if (isValidLink(link))
            {
                val response = http.connect(link.replace("embed", "check")).get().toString()
                val file = JSONObject(response).getString("file").replace("\\", "").replace("/", "//").replace(":////", "://")
                directLink = if (!file.isEmpty()) file else null
            }
        }
        catch (exception : Exception)
        {
            // Log
            directLink = null
        }
        finally {
            http.isSkipCache = auxCache
        }
        directLink
    }

    private fun isValidLink(link: String) : Boolean
    {
        return link.contains("s=izanagi") // TODO: Completar validación
    }

}