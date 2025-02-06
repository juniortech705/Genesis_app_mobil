package cd.zgeniuscoders.genesis.remote_controle.presentation.home

data class HomeState(
    val isBluetoothEnable: Boolean = false,
    val isTimerBottomSheetOpen: Boolean = false,
    val isPowerOnBottomSheetOpen: Boolean = false,
    val isPowerVolumeBottomSheetOpen: Boolean = false,
    val timerText: String = "00:00:00",
    val hours: String = "",
    var minutes: String = "",
    val secondes: String = "",
    val isPlaying: Boolean = false,
    val isTimerCountdownEnd: Boolean = false,
    val temperature: Float = 0.0f,
    val isDevicePower: Boolean = false,
    val timerStatus: TimerStatus = TimerStatus.Idle,
    val powerLevel: Int = 0
)