import jdk.nashorn.internal.ir.annotations.Ignore
import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.hoster.Izanagi
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class HosterIzanagiTest {

    @Test
    fun IzanagiValidDownloadLink() = runBlocking {
        val link = "https://s3.animeflv.com/embed.php?s=izanagi&v=ekZ5MFZiaHNqb1Y2aHBhSzJnZU9mYWFiVXdNelpUdm5La0dqVDg1c05EdGVDSm9rZjg2VHNYV0MrNGZ4anVDMFFyYk9FWWZVanJlTGhYeGN5ZTcraGtRdFVPVStwUTlBOTlzTld3YTZGVm91ODI1ZTQ1S0lrNWo2Z0ZPT3A4b3lNRE41ZnRUN2lsczNmaXNsM2lSbTZnPT0="
        val directLink = Izanagi().directLink(link).await()
        println(directLink)
        Assertions.assertAll(
                Executable { Assertions.assertNotNull(directLink) },
                Executable { Assertions.assertNotEquals("", directLink) }
        )
    }

    @Test
    fun IzanagiInvalidDownloadLink() = runBlocking {
        val link = "https://s3.animeflv.com/embed.php?s=izanagi&v=000000000000"
        val directLink = Izanagi().directLink(link).await()
        println(directLink)
        Assertions.assertNull(directLink)
    }

}