package cd.zgeniuscoders.genesis.common.ui.views.main

import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothDeviceModel

sealed interface ShareEvent {

    data object OnLogout : ShareEvent

    data object PowerOn : ShareEvent

    data object PowerOff : ShareEvent

    data object Buzzer : ShareEvent


    data object startSpeech: ShareEvent


    data class PowerUp(val value: String) : ShareEvent

    data class PowerDown(val value: String) : ShareEvent

    data class ConnectDevice(val device: BluetoothDeviceModel): ShareEvent
}