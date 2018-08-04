import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.hoster.Fenix
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class HosterFenixTest {

    @Test
    fun FenixValidDownloadLink() = runBlocking {
        val link = "https://s1.animeflv.net/embed.php?s=fenix&v=MUpOUmxkdldyekhZT0ZUZGN0RjFlb1FiQW96ZnhPQnFyaWY1U004Y0RPZTRVYXMrOVo5c2xWOGRmZ1RCaVlHdTNZelJZT3BWTlFIdlpSbXJNcklWNUg2aW5WbUx6MXVuMUFaVG1HY2FtK2hMa0MzZDJMeVptUVk5eUtVL1BVNmk="
        val directLink = Fenix().directLink(link).await()
        println(directLink)
        Assertions.assertAll(
                Executable { Assertions.assertNotNull(directLink) },
                Executable { Assertions.assertNotEquals("", directLink) }
        )
    }

    @Test
    fun FenixInvalidDownloadLink() = runBlocking {
        val link = "https://s3.animeflv.com/embed.php?s=izanagi&v=000000000000"
        val directLink = Fenix().directLink(link).await()
        println(directLink)
        Assertions.assertNull(directLink)
    }

}