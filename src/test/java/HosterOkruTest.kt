import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.hoster.Okru
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class HosterOkruTest {

    @Test
    fun OkruValidDownloadLink() = runBlocking {
        val link = "https://ok.ru/videoembed/1402100779631"
        val directLink = Okru().directLink(link).await()
        println(directLink)
        Assertions.assertAll(
                Executable { Assertions.assertNotNull(directLink) },
                Executable { Assertions.assertNotEquals("", directLink) }
        )
    }

    @Test
    fun OkruInvalidDownloadLink() = runBlocking {
        val link = "https://ok.ru/videoembed/00000000000000"
        val directLink = Okru().directLink(link).await()
        println(directLink)
        Assertions.assertNull(directLink)
    }

}