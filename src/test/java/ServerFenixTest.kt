import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.hosts.FenixServer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class ServerFenixTest {

    @Test
    fun FenixDownloadValidLink() = runBlocking {
        val link = "https://s1.animeflv.net/embed.php?s=fenix&v=MUpOUmxkdldyekhZT0ZUZGN0RjFlb1FiQW96ZnhPQnFyaWY1U004Y0RPZTRVYXMrOVo5c2xWOGRmZ1RCaVlHdTNZelJZT3BWTlFIdlpSbXJNcklWNUg2aW5WbUx6MXVuMUFaVG1HY2FtK2hMa0MzZDJMeVptUVk5eUtVL1BVNmk="
        val server = FenixServer()
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
    fun FenixDownloadInvalidLink() = runBlocking {
        val link = "https://s3.animeflv.com/embed.php?s=xxxxx&v=000000"
        val server = FenixServer()
        server.setUrl(link)
        server.process(null)?.join()
        val directLink = server.getDirectURL()
        println(directLink)
        Assertions.assertNull(directLink)
    }

}