import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.hoster.Maru
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class HosterMaruTest {

    @Test
    fun MaruDownloadValidLink() = runBlocking {
        val link = "https://my.mail.ru/video/embed/8995617145282887897"
        val directLink = Maru().directLink(link).await()
        println(directLink)
        Assertions.assertAll(
                Executable { Assertions.assertNotNull(directLink) },
                Executable { Assertions.assertNotEquals("", directLink) }
        )
    }

    @Test
    fun MaruDownloadInvalidLink() = runBlocking {
        val link = "https://my.mail.ru/video/embed/00000000000"
        val directLink = Maru().directLink(link).await()
        println(directLink)
        Assertions.assertNull(directLink)
    }

}