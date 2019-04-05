import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.hosts.Mp4UploadServer
import mx.com.nitrostudio.animechannel.models.entities.servers.hosts.OkruServer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class ServerOkruTest {

    @Test
    fun OkruDownloadValidLink() = runBlocking {
        val link = "https://ok.ru/videoembed/1402100779631"
        val server = OkruServer()
        server.setUrl(link)
        server.process(null)?.join()
        val directLink = server.getDirectURL()
        println(directLink)
        Assertions.assertAll(
                Executable { Assertions.assertNotNull(directLink) },
                Executable { Assertions.assertNotEquals("", directLink) }
        )
    }

    @Test
    fun OkruDownloadInvalidLink() = runBlocking {
        val link = "https://ok.ru/videoembed/00000000000000"
        val server = OkruServer()
        server.setUrl(link)
        server.process(null)?.join()
        val directLink = server.getDirectURL()
        println(directLink)
        Assertions.assertNull(directLink)
    }

}