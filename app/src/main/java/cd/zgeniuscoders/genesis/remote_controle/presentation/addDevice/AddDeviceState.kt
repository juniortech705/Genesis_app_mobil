package cd.zgeniuscoders.genesis.remote_controle.presentation.addDevice

import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothDevice


data class AddDeviceState(
    var isLoading: Boolean = true,
    var scannedDevices: List<BluetoothDevice> = emptyList(),
    val isConnecting: Boolean = false,
    val isConnected: Boolean = false,
    val errorMessage: String? = null
)