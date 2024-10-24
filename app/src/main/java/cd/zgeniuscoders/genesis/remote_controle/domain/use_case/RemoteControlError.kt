package cd.zgeniuscoders.genesis.remote_controle.domain.use_case

import cd.zgeniuscoders.genesis.remote_controle.domain.services.RemoteControlService
import kotlinx.coroutines.flow.SharedFlow

class RemoteControlError(private val service: RemoteControlService) {

    fun run(): SharedFlow<String> {
        return service.errors
    }

}