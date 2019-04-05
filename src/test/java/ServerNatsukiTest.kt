import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.hosts.NatsukiServer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class ServerNatsukiTest {

    @Test
    fun NatsukiDownloadValidLink() = runBlocking {
        val link = "https://s1.animeflv.net/embed.php?s=natsuki&v=L0U0WlNpL3V6ZHZHQ2hXSVVObHVGek8wQnN5aldSZWw3bUtFajB4dk55dkUwOURMd2JSODRGbVRJdUgxd1RSYUJNREVmN3RSeFAvOVdIbGFVOTd1K01Fd3ppb3Y3YWpsNXppUlh2TDRmbGh0dzVWQWJDSnRvaXZTekNHeDdYbG9pVDdIOWljeVhIcW5VeHRBTy9zZ1RnPT0="
        val server = NatsukiServer()
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
    fun NatsukiDownloadInvalidLink() = runBlocking {
        val link = "https://s1.animeflv.net/embed.php?s=natsuki&v=0000000000000000"
        val server = NatsukiServer()
        server.setUrl(link)
        server.process(null)?.join()
        val directLink = server.getDirectURL()
        println(directLink)
        Assertions.assertNull(directLink)
    }

}