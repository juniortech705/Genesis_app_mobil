package cd.zgeniuscoders.genesis.remote_controle.domain.use_case

import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothDevice
import cd.zgeniuscoders.genesis.remote_controle.domain.services.RemoteControlService
import kotlinx.coroutines.flow.StateFlow

class ScannedDevices(val service: RemoteControlService) {

    fun run(): StateFlow<List<BluetoothDevice>> {
        return service.scannedDevices
    }

}