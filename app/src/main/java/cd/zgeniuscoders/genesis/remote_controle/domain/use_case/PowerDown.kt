package cd.zgeniuscoders.genesis.remote_controle.domain.use_case

import cd.zgeniuscoders.genesis.remote_controle.domain.services.RemoteControlService

class PowerDown(val service: RemoteControlService) {

    suspend fun run(value: String) {
        service.powerDown(value)
    }

}