package mx.com.nitrostudio.animechannel.models.entities.servers.hoster

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import mx.com.nitrostudio.animechannel.services.jbro.Jbro
import java.util.regex.Pattern

class Mediafire : IHoster {

    override fun directLink(link: String): Deferred<String?> = async {
        var directLink : String? = null
        val http = Jbro.getInstance()
        val auxCache = http.isSkipCache
        http.isSkipCache = false
        try {
            if (isValidLink(link))
            {
                val response = http.connect(link).get().toString()
                val patternVideo = Pattern.compile("[\"']http://download(.*?)[\"']")
                val matcher = patternVideo.matcher(response)
                val file = if (matcher.find()) matcher.group(1) else null
                directLink = if (file != null && !file.isEmpty()) file else null
            }
        }
        catch (exception : Exception)
        {
            // Log
            exception.printStackTrace();
            directLink = null
        }
        finally {
            http.isSkipCache = auxCache
        }
        directLink
    }

    private fun isValidLink(link: String) : Boolean
    {
        return link.toLowerCase().contains("mediafire.com") // TODO: Completar validación
    }

}