import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.hoster.Natsuki
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class HosterNatsukiTest {

    @Test
    fun NatsukiValidDownloadLink() = runBlocking {
        val link = "https://s1.animeflv.net/embed.php?s=natsuki&v=TXhXTHJZcVQ1S2FNSW9lTjd3bTYzZHdoL3BEOFhpTytiMHM4NkkyUFdyaHB5V1hrSXZINUZRcm5oVjRnUWJMUXp5SzM5bjFkcUVyK1hncXRSc2tJKzNTWThnQmlSSS8vZWlxQWlpMmhrbjREeWlLSWVqK2J4dmRzQ1IxbUEvS01iTEdreFlNSmkvRlV3UzVhejZ0RGJ3PT0="
        val directLink = Natsuki().directLink(link).await()
        println(directLink)
        Assertions.assertAll(
                Executable { Assertions.assertNotNull(directLink) },
                Executable { Assertions.assertNotEquals("", directLink) }
        )
    }

    @Test
    fun NatsukiInvalidDownloadLink() = runBlocking {
        val link = "https://s3.animeflv.com/embed.php?s=izanagi&v=000000000000"
        val directLink = Natsuki().directLink(link).await()
        println(directLink)
        Assertions.assertNull(directLink)
    }

}