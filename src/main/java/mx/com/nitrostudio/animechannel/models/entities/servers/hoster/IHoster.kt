package mx.com.nitrostudio.animechannel.models.entities.servers.hoster

import kotlinx.coroutines.experimental.Deferred

interface IHoster {

    fun directLink(link : String) : Deferred<String?>

}