import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.hoster.RapidVideo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class HosterRapidVideoTest {

    @Test
    fun RapidvideoDownloadValidLink() = runBlocking {
        val link = "https://www.rapidvideo.com/e/G1MCV0ZY9O&q=480p"
        val directLink = RapidVideo().directLink(link).await()
        println(directLink)
        Assertions.assertAll(
                Executable { Assertions.assertNotNull(directLink) },
                Executable { Assertions.assertNotEquals("", directLink) }
        )
    }

    @Test
    fun RapidvideoDownloadInvalidLink() = runBlocking {
        val link = "https://www.rapidvideo.com/e/00000000000000&q=480p"
        val directLink = RapidVideo().directLink(link).await()
        println(directLink)
        Assertions.assertNull(directLink)
    }

}