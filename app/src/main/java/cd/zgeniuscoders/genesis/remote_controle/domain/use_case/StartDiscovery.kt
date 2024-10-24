package cd.zgeniuscoders.genesis.remote_controle.domain.use_case

import cd.zgeniuscoders.genesis.remote_controle.domain.services.RemoteControlService

class StartDiscovery(private val service: RemoteControlService) {

    fun run(){
        return service.startDiscovery()
    }

}