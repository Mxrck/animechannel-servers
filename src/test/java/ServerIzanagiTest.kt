import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.hosts.IzanagiServer
import mx.com.nitrostudio.animechannel.models.entities.servers.hosts.Mp4UploadServer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class ServerIzanagiTest {

    @Test
    fun IzanagiDownloadValidLink() = runBlocking {
        val link = "https://s3.animeflv.com/embed.php?s=izanagi&v=ekZ5MFZiaHNqb1Y2aHBhSzJnZU9mYWFiVXdNelpUdm5La0dqVDg1c05EdGVDSm9rZjg2VHNYV0MrNGZ4anVDMFFyYk9FWWZVanJlTGhYeGN5ZTcraGtRdFVPVStwUTlBOTlzTld3YTZGVm91ODI1ZTQ1S0lrNWo2Z0ZPT3A4b3lNRE41ZnRUN2lsczNmaXNsM2lSbTZnPT0="
        val server = IzanagiServer()
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
    fun IzanagiDownloadInvalidLink() = runBlocking {
        val link = "https://s3.animeflv.com/embed.php?s=xxxxx&v=000000"
        val server = IzanagiServer()
        server.setUrl(link)
        server.process(null)?.join()
        val directLink = server.getDirectURL()
        println(directLink)
        Assertions.assertNull(directLink)
    }

}