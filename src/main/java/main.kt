import mx.com.nitrostudio.animechannel.models.entities.servers.hosts.Fire
import mx.com.nitrostudio.animechannel.models.entities.servers.hosts.RapidVideoServer

fun main(args: Array<String>) {
    mediafireExample()

    /**
     * Ejemplo de URLs que llegarán a los parsers
     * Capítulo: Boruto: Naruto Next Generations Episodio 63
     * URL: https://animeflv.net/ver/49466/boruto-naruto-next-generations-63
            RV           => https://s1.animeflv.net/redirector.php?top=/ver/49466/boruto-naruto-next-generations-63&server=rv&value=FSR7Q9KPP2
            MEGA         => https://s1.animeflv.net/redirector.php?top=/ver/49466/boruto-naruto-next-generations-63&server=mega&value=!vv4xFSpK!AMP_XQAqhY-w1bok27A8ZwDpJggA-0oWsJ-ibtsfsmY
            STREAMANGO   => https://s1.animeflv.net/redirector.php?top=/ver/49466/boruto-naruto-next-generations-63&server=streamango&value=dntlqoqlbmnaolqm
            OPENLOAD     => https://s1.animeflv.net/redirector.php?top=/ver/49466/boruto-naruto-next-generations-63&server=openload&value=Xt9wx7vgABk
            OK (BORRADO) => https://s1.animeflv.net/redirector.php?top=/ver/49466/boruto-naruto-next-generations-63&server=ok&value=913127967343
            MP4          => https://s1.animeflv.net/redirector.php?top=/ver/49466/boruto-naruto-next-generations-63&server=mp4&value=s3ff4yo4jl1b
            YU           => https://s1.animeflv.net/redirector.php?top=/ver/49466/boruto-naruto-next-generations-63&server=yu&value=5Mwc73r3A51w
            MARU         => https://s1.animeflv.net/redirector.php?top=/ver/49466/boruto-naruto-next-generations-63&server=maru&value=8995617145282887826#budyak.rus#146
            NETU         => https://s1.animeflv.net/redirector.php?top=/ver/49466/boruto-naruto-next-generations-63&server=netu&value=cFhiU3lVUERJWGp6TlRTVFJpMUtOZz09
     */
    /*
    EXTRAS de otro anime con más servers:
            Izanagi      => https://s3.animeflv.com/embed.php?s=izanagi&v=ekZ5MFZiaHNqb1Y2aHBhSzJnZU9mYWFiVXdNelpUdm5La0dqVDg1c05EdGVDSm9rZjg2VHNYV0MrNGZ4anVDMFFyYk9FWWZVanJlTGhYeGN5ZTcraGtRdFVPVStwUTlBOTlzTld3YTZGVm91ODI1ZTQ1S0lrNWo2Z0ZPT3A4b3lNRE41ZnRUN2lsczNmaXNsM2lSbTZnPT0=
            Okru         => https://animeflv.net/redirector.php?top=/ver/49541/violet-evergarden-14&server=ok&value=927942380143
     */
    // megaExample()
    // RvExample()
    // StreamangoExample()
    // ...
}

fun mediafireExample()
{
    val demoRV = "https://www.rapidvideo.com/e/G1MCV0ZY9O&q=480p"
    val rapidScraper = RapidVideoServer()
    rapidScraper.setName("Rapidvideo")
    rapidScraper.setUrl(demoRV)
    rapidScraper.setEmbed(demoRV)
    rapidScraper.process(null)?.join()
    println(rapidScraper.getDirectURL())


    var urlDemo = "http://www.mediafire.com/file/0djwmh1yr014od3/Colaboraci%C3%B3n+Top+Anime-DongHua+Openings+Invierno+2018.xlsx"
    var fireScraper = Fire()
    fireScraper.setUrl(urlDemo)
    fireScraper.process(null)?.join()
    println(fireScraper.getDirectURL())
}