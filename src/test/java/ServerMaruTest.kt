import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.hosts.MaruServer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class ServerMaruTest {

    @Test
    fun MaruDownloadValidLink() = runBlocking {
        val link = "https://my.mail.ru/video/embed/8995617145282893183#budyak.rus#5503"
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
        val link = "https://my.mail.ru/video/embed/0000000000000000"
        val server = MaruServer()
        server.setUrl(link)
        server.process(null)?.join()
        val directLink = server.getDirectURL()
        println(directLink)
        Assertions.assertNull(directLink)
    }

}