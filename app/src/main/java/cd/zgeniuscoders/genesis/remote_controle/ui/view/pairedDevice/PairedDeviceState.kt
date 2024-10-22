package cd.zgeniuscoders.genesis.remote_controle.ui.view.pairedDevice

import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothDevice


data class PairedDeviceState(
    var isLoading: Boolean = true,
    var pairedDevices: List<BluetoothDevice> = emptyList(),
    val isConnecting: Boolean = false,
    val isConnected: Boolean = false,
    val errorMessage: String? = null
)