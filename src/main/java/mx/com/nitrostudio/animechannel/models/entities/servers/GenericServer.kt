package mx.com.nitrostudio.animechannel.models.entities.servers

import com.google.gson.annotations.SerializedName
import mx.com.nitrostudio.animechannel.models.webservice.ICallback

open class GenericServer : IServer {

    @SerializedName("name") protected var _name : String? = null
    @SerializedName("url") protected var _url : String? = null
    @SerializedName("embed") protected var _embed : String? = null
    @SerializedName("_direct_url") protected var _direct_url : String? = null
    @SerializedName("processable") protected var _processable = false

    override fun getName() : String? { return _name }
    override fun getEmbbed(): String? { return _embed }
    override fun getDirectURL(): String? { return _direct_url }
    override fun getURL(): String? { return _url }
    override fun isProcessable(): Boolean { return _processable }

    override fun setName(name: String?): IServer {
        _name = name
        return this
    }

    override fun setUrl(url: String?): IServer {
        _url = url
        return this
    }

    override fun setEmbed(embed: String?): IServer {
        _embed = embed
        return this
    }

    override fun setDirectUrl(directUrl: String?): IServer {
        _direct_url = directUrl
        return this
    }

    override fun process(callback: ICallback<String?>?): Thread? {
        callback?.onStart()
        callback?.onError("Servidor no disponible")
        return null
    }

}