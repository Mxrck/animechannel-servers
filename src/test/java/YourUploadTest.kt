import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.hoster.YourUpload
import mx.com.nitrostudio.animechannel.models.entities.servers.hosts.YourUploadServer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class YourUploadTest {
    @Test
    fun runHosterWithValidLink() = runBlocking {
        val link = "https://www.yourupload.com/embed/5Mwc73r3A51w"
        val directLink = YourUpload().directLink(link).await()
        println("direct link: $directLink")
        Assertions.assertAll(
                Executable { Assertions.assertNotNull(directLink) },
                Executable { Assertions.assertNotEquals("", directLink) }
        )
    }
    @Test
    fun runHosterWithInvalidLink() = runBlocking {
        val link = "https://www.yourupload.comafds/embed/5Mwc73r3A51w"
        val directLink = YourUpload().directLink(link).await()
        println("direct link: $directLink")
        Assertions.assertNull(directLink)
    }

    @Test
    fun runServerWithValidLink() = runBlocking {
        val link = "https://s1.animeflv.net/redirector.php?top=/ver/49466/boruto-naruto-next-generations-63&server=yu&value=5Mwc73r3A51w"
        val server = YourUploadServer()
        server.setUrl(link)
        server.process(null)?.join()
        val directLink = server.getDirectURL()
        Assertions.assertAll(
                Executable { Assertions.assertNotNull(directLink) },
                Executable { Assertions.assertNotEquals("", directLink) }
        )
    }

    @Test
    fun runServerWithInvalidLink() = runBlocking {
        val link = "https://s1.animeflv.netasd/redirector.php?top=/ver/49466/boruto-naruto-next-generations-63&server=yu&value=5Mwc73r3A51w"
        val server = YourUploadServer()
        server.setUrl(link)
        server.process(null)?.join()
        val directLink = server.getDirectURL()
        Assertions.assertNull(directLink)
    }
}