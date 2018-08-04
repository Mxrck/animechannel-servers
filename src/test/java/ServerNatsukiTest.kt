import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.hosts.NatsukiServer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class ServerNatsukiTest {

    @Test
    fun NatsukiDownloadValidLink() = runBlocking {
        val link = "https://s1.animeflv.net/embed.php?s=natsuki&v=TXhXTHJZcVQ1S2FNSW9lTjd3bTYzZHdoL3BEOFhpTytiMHM4NkkyUFdyaHB5V1hrSXZINUZRcm5oVjRnUWJMUXp5SzM5bjFkcUVyK1hncXRSc2tJKzNTWThnQmlSSS8vZWlxQWlpMmhrbjREeWlLSWVqK2J4dmRzQ1IxbUEvS01iTEdreFlNSmkvRlV3UzVhejZ0RGJ3PT0="
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
        val link = "https://s3.animeflv.com/embed.php?s=xxxxx&v=000000"
        val server = NatsukiServer()
        server.setUrl(link)
        server.process(null)?.join()
        val directLink = server.getDirectURL()
        println(directLink)
        Assertions.assertNull(directLink)
    }

}