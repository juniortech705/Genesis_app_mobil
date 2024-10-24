package cd.zgeniuscoders.genesis.common.ui.views.main

sealed interface ShareEvent {

    data object OnLogout : ShareEvent

    data object PowerOn : ShareEvent

    data object PowerOff : ShareEvent

    data object Buzzer : ShareEvent
}