package cd.zgeniuscoders.genesis.common.ui.views.main

import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothDevice
import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothMessage

data class ShareState(
    val scannedDevices: List<BluetoothDevice> = emptyList(),
    val pairedDevices: List<BluetoothDevice> = emptyList(),
    val isConnected: Boolean = false,
    val isConnecting: Boolean = false,
    val errorMessage: String? = null,
    val isScanning: Boolean = true,
    val powerLevel: Int = 0,
    val messages: List<BluetoothMessage> = emptyList(),
    val speechMessage: String = ""
)
