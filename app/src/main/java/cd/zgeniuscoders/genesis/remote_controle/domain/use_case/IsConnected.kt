package cd.zgeniuscoders.genesis.remote_controle.domain.use_case

import cd.zgeniuscoders.genesis.remote_controle.domain.services.RemoteControlService
import kotlinx.coroutines.flow.StateFlow

class IsConnected(private val service: RemoteControlService) {

    fun run(): StateFlow<Boolean> {
        return service.isConnected
    }

}