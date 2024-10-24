package cd.zgeniuscoders.genesis.remote_controle.domain.use_case

import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothDevice
import cd.zgeniuscoders.genesis.remote_controle.domain.services.ConnectionResult
import cd.zgeniuscoders.genesis.remote_controle.domain.services.RemoteControlService
import kotlinx.coroutines.flow.Flow

class ConnectToDevice(private val service: RemoteControlService) {

    fun run(device: BluetoothDevice): Flow<ConnectionResult> {
        return service.connectToDevice(device)
    }


}