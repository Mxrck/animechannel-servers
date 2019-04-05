import jdk.nashorn.internal.ir.annotations.Ignore
import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.hoster.Mediafire
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

@Ignore
class HosterMediafireTest {

    @Test
    fun MediafireDownloadValidLink() = runBlocking {
        val link = "http://www.mediafire.com/file/0djwmh1yr014od3/Colaboraci%C3%B3n+Top+Anime-DongHua+Openings+Invierno+2018.xlsx"
        val directLink = Mediafire().directLink(link).await()
        println(directLink)
        Assertions.assertAll(
                Executable { Assertions.assertNotNull(directLink) },
                Executable { Assertions.assertNotEquals("", directLink) }
        )
    }

    @Test
    fun MediafireDownloadInvalidLink() = runBlocking {
        val link = "http://www.mediafire.com/file/00000000000"
        val directLink = Mediafire().directLink(link).await()
        println(directLink)
        Assertions.assertNull(directLink)
    }

}