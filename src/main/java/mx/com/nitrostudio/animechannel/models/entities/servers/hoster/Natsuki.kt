package mx.com.nitrostudio.animechannel.models.entities.servers.hoster

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import mx.com.nitrostudio.animechannel.services.jbro.Jbro

class Natsuki :  IHoster {

    override fun directLink(link: String): Deferred<String?> = async {
        var directLink : String? = null
        val http = Jbro.getInstance()
        val auxCache = http.isSkipCache
        http.isSkipCache = false
        try {
            if (isValidLink(link))
            {
                val response = http.connect(link.replace("embed", "check")).get().toString()
                val type = object : TypeToken<Map<String,String>>() {}.type
                val map = Gson().fromJson<Map<String,String>>(response, type)
                val file = map["file"]
                directLink = if (file != null && !file.isEmpty()) file else null
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
        return link.contains("s=natsuki") // TODO: Completar validación
    }

}