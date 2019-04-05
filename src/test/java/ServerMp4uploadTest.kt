import jdk.nashorn.internal.ir.annotations.Ignore
import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.hosts.Mp4UploadServer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class ServerMp4uploadTest {

    @Test
    @Ignore
    fun Mp4uploadDownloadValidLink() = runBlocking {
        val link = "https://s1.animeflv.net/redirector.php?top=/ver/49466/boruto-naruto-next-generations-63&server=mp4&value=s3ff4yo4jl1b"
        val server = Mp4UploadServer()
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
    @Ignore
    fun Mp4uploadDownloadInvalidLink() = runBlocking {
        val link = "https://s1.animeflv.net/redirector.php?top=/ver/49466/boruto-naruto-next-generations-63&server=XXX&value=000000"
        val server = Mp4UploadServer()
        server.setUrl(link)
        server.process(null)?.join()
        val directLink = server.getDirectURL()
        println(directLink)
        Assertions.assertNull(directLink)
    }

}