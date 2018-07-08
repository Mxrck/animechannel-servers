package mx.com.nitrostudio.animechannel.models.entities.servers.hoster

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import mx.com.nitrostudio.animechannel.services.jbro.Jbro
import org.jsoup.Jsoup

class RapidVideo : IHoster {

    override fun directLink(link: String): Deferred<String?> = async {
        var directLink : String? = null
        val http = Jbro.getInstance()
        val auxCache = http.isSkipCache
        http.isSkipCache = false
        try {
            if (isValidLink(link))
            {
                val response = http.connect(link).get().toString()
                val document = Jsoup.parse(response)
                val element = document.select("video source").first()
                val file = element?.attr("src")
                directLink = if (file != null && !file.isEmpty()) file else null
            }
        }
        catch (exception : Exception)
        {
            // Log
            exception.printStackTrace()
            directLink = null
        }
        finally {
            http.isSkipCache = auxCache
        }
        directLink
    }

    private fun isValidLink(link: String) : Boolean
    {
        return link.toLowerCase().contains("rapidvideo.com") // TODO: Completar validación
    }

}