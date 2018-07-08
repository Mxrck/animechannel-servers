import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.hosts.Mp4UploadServer
import mx.com.nitrostudio.animechannel.models.entities.servers.hosts.OkruServer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class ServerOkruTest {

    @Test
    fun OkruDownloadValidLink() = runBlocking {
        val link = "https://animeflv.net/redirector.php?top=/ver/49541/violet-evergarden-14&server=ok&value=927942380143"
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
        val link = "https://s1.animeflv.net/redirector.php?top=/ver/49466/boruto-naruto-next-generations-63&server=xx&value=00000000"
        val server = OkruServer()
        server.setUrl(link)
        server.process(null)?.join()
        val directLink = server.getDirectURL()
        println(directLink)
        Assertions.assertNull(directLink)
    }

}