package cd.zgeniuscoders.genesis.remote_controle.ui.framework.extensions

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothDeviceModel

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceModel(): BluetoothDeviceModel {
    return BluetoothDeviceModel(
        name = name ?: "Unknown device name",
        address = address
    )
}
