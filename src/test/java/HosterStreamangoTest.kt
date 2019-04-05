import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.hoster.Izanagi
import mx.com.nitrostudio.animechannel.models.entities.servers.hoster.Streamango
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class HosterStreamangoTest {

    @Test
    fun StramangoValidDownloadLink() = runBlocking {
        val link = "https://streamango.com/embed/lnnnmaaqdptcsecn"
        val directLink = Streamango().directLink(link).await()
        println(directLink)
        Assertions.assertAll(
                Executable { Assertions.assertNotNull(directLink) },
                Executable { Assertions.assertNotEquals("", directLink) }
        )
    }

    @Test
    fun StramangoInvalidDownloadLink() = runBlocking {
        val link = "https://streamango.com/embed/xxxxxxxxxxxxxx"
        val directLink = Streamango().directLink(link).await()
        println(directLink)
        Assertions.assertNull(directLink)
    }

}