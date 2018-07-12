package mx.com.nitrostudio.animechannel.models.entities.servers.hoster

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import mx.com.nitrostudio.animechannel.services.jbro.Jbro
import org.jsoup.Jsoup


class Okru :  IHoster {

    override fun directLink(link: String): Deferred<String?> = async {
        var directLink : String? = null
        val http = Jbro.getInstance()
        val auxCache = http.isSkipCache
        http.isSkipCache = false
        try {
            if (isValidLink(link))
            {
                val fullJson= Jsoup.connect(link).get().select("div[data-module='OKVideo']").first().attr("data-options")
                val cutJson="{"+fullJson.substring(fullJson.lastIndexOf("\\\"videos"), fullJson.indexOf(",\\\"metadataEmbedded")).replace("\\&quot;", "\"").replace("\\u0026", "&").replace("\\", "").replace("%3B", ";") + "}"
                val type = object : TypeToken<Map<String, JsonElement>>() {}.type
                val map = Gson().fromJson<Map<String,JsonElement>>(cutJson, type)
                val array = map["videos"]?.asJsonArray
                if (array != null)
                {
                    for (i in 0 until array.size()) {
                        val `object` = array.get(i).asJsonObject
                        when (`object`.get("name").asString) {
                            "hd" -> {
                                val file=`object`.get("url").asString
                                directLink = if (!file.isEmpty()) file else null
                            }
                        }
                    }
                }
            }
        }
        catch (exception : Exception)
        {
            // Log
            directLink = null
            exception.printStackTrace()
        }
        finally {
            http.isSkipCache = auxCache
        }
        directLink
    }

    private fun isValidLink(link: String) : Boolean
    {
        return link.contains("ok.ru") // TODO: Completar validación
    }

}