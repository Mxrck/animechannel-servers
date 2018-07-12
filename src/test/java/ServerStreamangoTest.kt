import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.hosts.StreamangoServer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class ServerStreamangoTest {

    @Test
    fun Mp4uploadDownloadValidLink() = runBlocking {
        val link = "https://s1.animeflv.net/redirector.php?top=/ver/49466/boruto-naruto-next-generations-63&server=streamango&value=dntlqoqlbmnaolqm"
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
    fun Mp4uploadDownloadInvalidLink() = runBlocking {
        val link = "https://s1.animeflv.net/redirector.php?top=/ver/49466/boruto-naruto-next-generations-63&server=xxxxxx&value=000000"
        val server = StreamangoServer()
        server.setUrl(link)
        server.process(null)?.join()
        val directLink = server.getDirectURL()
        println(directLink)
        Assertions.assertNull(directLink)
    }

}