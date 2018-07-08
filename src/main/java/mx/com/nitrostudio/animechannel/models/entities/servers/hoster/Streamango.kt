package mx.com.nitrostudio.animechannel.models.entities.servers.hoster

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import mx.com.nitrostudio.animechannel.services.jbro.Jbro
import java.util.regex.Pattern

class Streamango : IHoster {

    override fun directLink(link: String): Deferred<String?> = async{
        var directLink : String? = null
        val http = Jbro.getInstance()
        val auxCache = http.isSkipCache
        http.isSkipCache = false
        try {
            if (isValidLink(link))
            {
                val response = http.connect(link).get().toString()
                val pattern = Pattern.compile("type:\"video/mp4\",src:d\\('([^']+)',(\\d+)\\)")
                val matcher = pattern.matcher(response)
                if (matcher.find())
                {
                    val hash = matcher.group(1)
                    val key = matcher.group(2).toInt()
                    val file = decode(hash, key)
                    var uncleanedLink = if (file != null && !file.isEmpty()) file else null
                    if (uncleanedLink != null && uncleanedLink.startsWith("//")) uncleanedLink = uncleanedLink.replaceFirst("//", "https://")
                    directLink = uncleanedLink
                }
                directLink
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
        return link.contains("streamango.com")
    }

    private fun decode(url: String, mask: Int): String? {
        val key = "=/+9876543210zyxwvutsrqponmlkjihgfedcbaZYXWVUTSRQPONMLKJIHGFEDCBA"
        val result = StringBuffer()
        val u = url.replace("[^A-Za-z0-9\\+\\/\\=]".toRegex(), "")
        var idx = 0
        while (idx < u.length) {
            val a = key.indexOf(u.substring(idx, idx + 1))
            idx++
            val b = key.indexOf(u.substring(idx, idx + 1))
            idx++
            val c = key.indexOf(u.substring(idx, idx + 1))
            idx++
            val d = key.indexOf(u.substring(idx, idx + 1))
            idx++
            val s1 = a shl 0x2 or (b shr 0x4) xor mask
            result.append(Character.valueOf(s1.toChar()))
            val s2 = b and 0xf shl 0x4 or (c shr 0x2)
            if (c != 0x40) {
                result.append(Character.valueOf(s2.toChar()))
            }
            val s3 = c and 0x3 shl 0x6 or d
            if (d != 0x40) {
                result.append(Character.valueOf(s3.toChar()))
            }
        }
        return result.toString()
    }

}