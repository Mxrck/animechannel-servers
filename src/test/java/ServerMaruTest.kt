import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.hosts.MaruServer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class ServerMaruTest {

    @Test
    fun MaruDownloadValidLink() = runBlocking {
        val link = "https://animeflv.net/redirector.php?top=/ver/49535/hataraku-saibou-tv-1&server=maru&value=8995617145282887897#budyak.rus#217"
        val server = MaruServer()
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
    fun MaruDownloadInvalidLink() = runBlocking {
        val link = "https://animeflv.net/redirector.php?top=/ver/49535/hataraku-saibou-tv-1&server=maru&value=00000000"
        val server = MaruServer()
        server.setUrl(link)
        server.process(null)?.join()
        val directLink = server.getDirectURL()
        println(directLink)
        Assertions.assertNull(directLink)
    }

}