import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.hosts.RapidVideoServer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class ServerRapidvideoTest {

    @Test
    fun RapidvideoDownloadValidLink() = runBlocking {
        val link = "https://www.rapidvideo.com/e/G1MCV0ZY9O&q=480p"
        val server = RapidVideoServer()
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
    fun RapidvideoDownloadInalidLink() = runBlocking {
        val link = "https://www.rapidvideo.com/e/000000000000=480p"
        val server = RapidVideoServer()
        server.setUrl(link)
        server.process(null)?.join()
        val directLink = server.getDirectURL()
        println(directLink)
        Assertions.assertNull(directLink)
    }

}