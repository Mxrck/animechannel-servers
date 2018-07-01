package mx.com.nitrostudio.animechannel.models.entities.servers

import mx.com.nitrostudio.animechannel.models.webservice.ICallback

interface IServer {

    fun getName() : String?
    fun getEmbbed() : String?
    fun getDirectURL() : String?
    fun getURL() : String?

    fun setName(name : String?) : IServer
    fun setEmbed(embed : String?) : IServer
    fun setDirectUrl(directUrl : String?) : IServer
    fun setUrl(url : String?) : IServer

    fun isProcessable() : Boolean

    fun process(callback : ICallback<String?>?) : Thread?

}