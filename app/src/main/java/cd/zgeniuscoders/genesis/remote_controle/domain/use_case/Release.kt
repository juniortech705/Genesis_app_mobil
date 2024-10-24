package cd.zgeniuscoders.genesis.remote_controle.domain.use_case

import cd.zgeniuscoders.genesis.remote_controle.domain.services.RemoteControlService

class Release(val service: RemoteControlService) {

    fun run() {
        service.release()
    }

}