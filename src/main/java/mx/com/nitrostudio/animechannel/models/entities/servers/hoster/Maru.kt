package mx.com.nitrostudio.animechannel.models.entities.servers.hoster

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import mx.com.nitrostudio.animechannel.services.jbro.Jbro
import java.util.regex.Pattern

class Maru : IHoster {

    override fun directLink(link: String): Deferred<String?> = async {
        var directLink : String? = null
        val http = Jbro.getInstance()
        val auxCache = http.isSkipCache
        http.isSkipCache = false
        try {
            if (isValidLink(link))
            {
                val link = link.replace("video/embed","+/video/meta")
                val response = http.connect(link).get().toString()
                val patternVideo = Pattern.compile("videos\":\\[\\{\"url\":\"(.*)\",\".*\"key\":\"720p")
                val matcher = patternVideo.matcher(response)
                val file = if (matcher.find()) matcher.group(1) else null
                var uncleanedLink = if (file != null && !file.isEmpty()) file else null
                if (uncleanedLink != null && uncleanedLink.startsWith("//")){
                    uncleanedLink = uncleanedLink.replaceFirst("//", "https://")
                    uncleanedLink = uncleanedLink.replace("slave[]","slave%5b%5d")
                }
                directLink = uncleanedLink
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
        return link.toLowerCase().contains("mail.ru") // TODO: Completar validación
    }

}