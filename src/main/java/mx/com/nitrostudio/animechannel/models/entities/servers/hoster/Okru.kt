package mx.com.nitrostudio.animechannel.models.entities.servers.hoster

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import mx.com.nitrostudio.animechannel.services.jbro.Jbro
import org.json.JSONObject
import org.jsoup.Jsoup
import java.util.regex.Pattern
import org.json.JSONArray



class Okru :  IHoster {

    override fun directLink(link: String): Deferred<String?> = async {
        var directLink : String? = null
        val http = Jbro.getInstance()
        val auxCache = http.isSkipCache
        http.isSkipCache = false
        try {
            if (isValidLink(link))
            {
                val okruLink = extractOkruLink(http.connect(link).get().toString())
                val fullJson= Jsoup.connect(okruLink).get().select("div[data-module='OKVideo']").first().attr("data-options")
                val cutJson="{"+fullJson.substring(fullJson.lastIndexOf("\\\"videos"), fullJson.indexOf(",\\\"metadataEmbedded")).replace("\\&quot;", "\"").replace("\\u0026", "&").replace("\\", "").replace("%3B", ";") + "}"
                val array = JSONObject(cutJson).getJSONArray("videos")
                for (i in 0 until array.length()) {
                    val `object` = array.getJSONObject(i)
                    when (`object`.getString("name")) {
                        "hd" -> {
                            val file=`object`.getString("url")
                            directLink = if (!file.isEmpty()) file else null
                        }
                    }
                }
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

    fun extractOkruLink(html: String): String {
        val matcher = Pattern.compile("\"(https://ok\\.ru.*)\"").matcher(html)
        matcher.find()
        return matcher.group(1)
    }

}