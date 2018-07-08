package mx.com.nitrostudio.animechannel.models.entities.servers.hoster

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import mx.com.nitrostudio.animechannel.services.jbro.Jbro
import org.jsoup.Jsoup
import java.util.regex.Pattern

class RapidVideo : IHoster {

    override fun directLink(link: String): Deferred<String?> = async {
        var directLink : String? = null
        val http = Jbro.getInstance()
        val auxCache = http.isSkipCache
        http.isSkipCache = false
        try {
            if (isValidLink(link))
            {
                val rvLink="&q=720p|&q=480p|&q=360p".toRegex().replace(link,"")
                val file = getRapidVideoLink(Jsoup.connect("$rvLink&q=720p").get().html())
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
        return link.toLowerCase().contains("&server=rv") // TODO: Completar validación
    }

    private fun getRapidVideoLink(link: String): String
    {
        val pattern = Pattern.compile("\"(http.*\\.mp4)\"")
        val matcher = pattern.matcher(link)
        matcher.find()
        return matcher.group(1)
    }

}