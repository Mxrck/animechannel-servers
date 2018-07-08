package mx.com.nitrostudio.animechannel.models.entities.servers.hoster

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import mx.com.nitrostudio.animechannel.services.jbro.Jbro
import mx.com.nitrostudio.animechannel.services.jbro.entities.Response

class YourUpload : IHoster {
    override fun directLink(link: String): Deferred<String?> = async {
        try {
            val response = Jbro.getInstance().getContents(link).await()
            val regex = """jwplayerOptions.*?file:.*?['"]+(.*?)['"]+""".toRegex()
            val match = regex.find(response)
            val (url) = match!!.destructured
            val responseWithHeader = Jbro.getInstance().addHeader("Referer",link).getContentsResponse(url).await()
            val finalUrl = responseWithHeader.url
            finalUrl
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    fun Jbro.getContentsResponse(url: String): Deferred<Response> = async {
        setFollowRedirects(false)
        val auxCache = isSkipCache
        isSkipCache = false
        val result = connect(url).get()
        isSkipCache = auxCache
        setFollowRedirects(true)
        result
    }

    fun Jbro.getContents(url: String): Deferred<String> = async {
        val auxCache = isSkipCache
        isSkipCache = false
        val result = connect(url).get().toString()
        isSkipCache = auxCache
        result
    }

    fun Jbro.getUrl(url: String): Deferred<String?> = async {
        val auxCache = isSkipCache
        isSkipCache = false
        val result = connect(url).get().url
        isSkipCache = auxCache
        if (result!=url)result else null
    }

}