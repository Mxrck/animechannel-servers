package mx.com.nitrostudio.animechannel.models.entities.servers.hoster

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import mx.com.nitrostudio.animechannel.services.jbro.Jbro
import java.util.regex.Pattern

class Mp4Upload : IHoster {

    override fun directLink(link: String): Deferred<String?> = async {
        var directLink : String? = null
        val http = Jbro.getInstance()
        val auxCache = http.isSkipCache
        http.isSkipCache = false
        try {
            if (isValidLink(link))
            {
                val response = http.connect(link).get().toString()
                val patternVideo = Pattern.compile("eval\\(function\\(.+\\)\\{.+return.+\\}\\((.*?)\\.split")
                val matcher = patternVideo.matcher(response)
                val params = if (matcher.find()) matcher.group(1) else null
                val file = mp4uploadFunction(params)
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

    private fun mp4uploadFunction(params : String?) : String?
    {
        try {
            var pattern = Pattern.compile("\\'(.*?[^\\\\])\\',(\\d+),(\\d+),\\'(.*?)\\'")
            var matcher = pattern.matcher(params)
            if  (matcher.find())
            {
                var p = matcher.group(1).replace(Regex("\\\\"), "")
                val a = Integer.parseInt(matcher.group(2))
                var c = Integer.parseInt(matcher.group(3))
                val k = matcher.group(4).split("|")
                while (c != 0) {
                    c--
                    if (k[c].isNotEmpty())
                        p = p.replace(Regex("\\b" + Integer.toString(c, a) + "\\b"), k[c])
                }
                if (p != null)
                {
                    pattern = Pattern.compile("[\"']file[\"']\\s*:\\s*[\"\'](.*?)[\"\']")
                    matcher = pattern.matcher(p)
                    if (matcher.find())
                    {
                        return matcher.group(1)
                    }
                }
            }
        }
        catch (exception : Exception)
        {
            return null
        }
        return null
    }

    private fun isValidLink(link: String) : Boolean
    {
        return link.toLowerCase().contains("mp4upload") // TODO: Completar validación
    }

}