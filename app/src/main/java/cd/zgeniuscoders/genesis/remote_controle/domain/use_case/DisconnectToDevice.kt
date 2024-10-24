package cd.zgeniuscoders.genesis.remote_controle.domain.use_case

import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothDevice
import cd.zgeniuscoders.genesis.remote_controle.domain.services.RemoteControlService

class DisconnectToDevice(private val service: RemoteControlService) {

    fun run(){
        return service.closeConnection()
    }

}