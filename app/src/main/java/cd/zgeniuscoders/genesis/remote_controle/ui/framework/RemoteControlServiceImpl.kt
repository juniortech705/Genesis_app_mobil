package cd.zgeniuscoders.genesis.remote_controle.ui.framework

import cd.zgeniuscoders.genesis.remote_controle.domain.services.BluetoothController
import cd.zgeniuscoders.genesis.remote_controle.domain.services.RemoteControlService

class RemoteControlServiceImpl(
    private val bluetoothController: BluetoothController,
): RemoteControlService {

    override suspend fun powerOn(){
        bluetoothController.trySendMessage("on")
    }

    override suspend fun buzzer(){
        bluetoothController.trySendMessage("")
    }

    override suspend fun startDiscovery(){
        bluetoothController.startDiscovery()
    }

    override suspend fun stopDiscovery(){
        bluetoothController.stopDiscovery()
    }

    override suspend fun connectToDevice(){

    }

}