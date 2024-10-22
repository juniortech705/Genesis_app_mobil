package cd.zgeniuscoders.genesis.remote_controle.domain.models

typealias BluetoothDeviceModel = BluetoothDevice

data class BluetoothDevice(
    val name: String,
    val address: String,
)