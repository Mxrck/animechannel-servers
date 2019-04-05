import kotlinx.coroutines.experimental.runBlocking
import mx.com.nitrostudio.animechannel.models.entities.servers.hoster.Natsuki
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class HosterNatsukiTest {

    @Test
    fun NatsukiValidDownloadLink() = runBlocking {
        val link = "https://s1.animeflv.net/embed.php?s=natsuki&v=L0U0WlNpL3V6ZHZHQ2hXSVVObHVGek8wQnN5aldSZWw3bUtFajB4dk55dkUwOURMd2JSODRGbVRJdUgxd1RSYUJNREVmN3RSeFAvOVdIbGFVOTd1K01Fd3ppb3Y3YWpsNXppUlh2TDRmbGh0dzVWQWJDSnRvaXZTekNHeDdYbG9pVDdIOWljeVhIcW5VeHRBTy9zZ1RnPT0="
        val directLink = Natsuki().directLink(link).await()
        println(directLink)
        Assertions.assertAll(
                Executable { Assertions.assertNotNull(directLink) },
                Executable { Assertions.assertNotEquals("", directLink) }
        )
    }

    @Test
    fun NatsukiInvalidDownloadLink() = runBlocking {
        val link = "https://s1.animeflv.net/embed.php?s=natsuki&v=00000000000000000000"
        val directLink = Natsuki().directLink(link).await()
        println(directLink)
        Assertions.assertNull(directLink)
    }

}