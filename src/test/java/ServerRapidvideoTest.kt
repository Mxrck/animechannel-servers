import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.hosts.RapidVideoServer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class ServerRapidvideoTest {

    @Test
    fun RVDownloadLink() = runBlocking {
        val link = "https://s1.animeflv.net/redirector.php?top=/ver/49466/boruto-naruto-next-generations-63&server=rv&value=FSR7Q9KPP2"
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

}