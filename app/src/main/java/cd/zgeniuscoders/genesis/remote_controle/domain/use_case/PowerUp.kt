package cd.zgeniuscoders.genesis.remote_controle.domain.use_case

import cd.zgeniuscoders.genesis.remote_controle.domain.services.RemoteControlService

class PowerUp(val service: RemoteControlService) {

    suspend fun run() {
        service.powerUp()
    }

}