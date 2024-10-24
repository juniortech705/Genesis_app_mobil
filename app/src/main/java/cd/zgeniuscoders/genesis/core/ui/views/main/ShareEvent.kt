package cd.zgeniuscoders.genesis.common.ui.views.main

import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothDeviceModel

sealed interface ShareEvent {

    data object OnLogout : ShareEvent

    data object PowerOn : ShareEvent

    data object PowerOff : ShareEvent

    data object Buzzer : ShareEvent


    data object PowerUp: ShareEvent

    data object PowerDown: ShareEvent

    data class ConnectDevice(val device: BluetoothDeviceModel): ShareEvent
}