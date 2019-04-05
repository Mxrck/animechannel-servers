import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.hosts.StreamangoServer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class ServerStreamangoTest {

    @Test
    fun streamangoDownloadValidLink() = runBlocking {
        val link = "https://streamango.com/embed/lnnnmaaqdptcsecn"
        val server = StreamangoServer()
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
    fun streamangoDownloadInvalidLink() = runBlocking {
        val link = "https://streamango.com/embed/0000000000"
        val server = StreamangoServer()
        server.setUrl(link)
        server.process(null)?.join()
        val directLink = server.getDirectURL()
        println(directLink)
        Assertions.assertNull(directLink)
    }

}