package cd.zgeniuscoders.genesis.remote_controle.ui.view.home

sealed class HomeEvent {

    data class OnSetTimer(val value: Boolean): HomeEvent()
    data class OnSetPower(val value: Boolean): HomeEvent()
    data class OnPowerOnBottomSheet(val value: Boolean): HomeEvent()
    data object OnStopBuzzer: HomeEvent()

    data class UpdateHour(val value: String) : HomeEvent()
    data class UpdateMinute(val value: String) : HomeEvent()
    data class UpdateTemperature(val value: Float) : HomeEvent()

    data object OnLaunchTimer: HomeEvent()
    data object OnStopTimer: HomeEvent()
    data object OnRunDevice: HomeEvent()

    data object OnPowerUp: HomeEvent()
    data object OnPowerDown: HomeEvent()
}
