import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.hoster.Mp4Upload
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class HosterMp4uploadTest {

    @Test
    fun Mp4uploadDownloadLink() = runBlocking {
        val link = "https://www.mp4upload.com/embed-s3ff4yo4jl1b.html"
        val directLink = Mp4Upload().directLink(link).await()
        println(directLink)
        Assertions.assertAll(
                Executable { Assertions.assertNotNull(directLink) },
                Executable { Assertions.assertNotEquals("", directLink) }
        )
    }

}