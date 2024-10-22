package cd.zgeniuscoders.genesis.remote_controle.ui.view.pairedDevice

import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothDevice

sealed class PairedDeviceEvent {

    data class OnConnectedToDevice(val device: BluetoothDevice): PairedDeviceEvent()
    data class OnDisconnectedToDevice(val device: BluetoothDevice): PairedDeviceEvent()

}