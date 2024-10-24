package cd.zgeniuscoders.genesis.remote_controle.domain.use_case

import cd.zgeniuscoders.genesis.remote_controle.domain.services.RemoteControlService

class PowerOff(val service: RemoteControlService) {

    suspend fun run(){
        service.powerOff()
    }

}