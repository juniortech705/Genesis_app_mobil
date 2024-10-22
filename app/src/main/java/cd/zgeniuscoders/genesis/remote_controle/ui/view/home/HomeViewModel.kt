package cd.zgeniuscoders.genesis.remote_controle.ui.view.home

import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.genesis.common.domain.models.TimeFormatExt.timeFormat
import cd.zgeniuscoders.genesis.remote_controle.ui.framework.NotificationService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class HomeViewModel(
    private val context: Context,
    private val notificationService: NotificationService
) : ViewModel() {

    private val bluetoothManager by lazy {
        context.getSystemService(BluetoothManager::class.java)
    }
    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    private var countDownTimer: CountDownTimer? = null

    var totalTimeMillis by mutableLongStateOf(0L)
    var timeLeft by mutableLongStateOf(totalTimeMillis)

    val countDownInterval = 1000L // 1 seconds is the lowest

    var state by mutableStateOf(HomeState())

    fun onTriggerEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnSetTimer -> state =
                state.copy(isTimerBottomSheetOpen = event.value)

            is HomeEvent.OnSetPower -> state =
                state.copy(isPowerVolumeBottomSheetOpen = event.value)

            is HomeEvent.UpdateHour -> state = state.copy(minutes = event.value)
            is HomeEvent.UpdateMinute -> state = state.copy(secondes = event.value)
            is HomeEvent.UpdateTemperature -> {}
            HomeEvent.OnLaunchTimer -> startCountDownTimer()
            is HomeEvent.OnPowerOnBottomSheet -> state =
                state.copy(isPowerOnBottomSheetOpen = event.value)

            HomeEvent.OnRunDevice -> runDevice()
            HomeEvent.OnStopTimer -> {
                stopCountDownTimer()
                resetCountDownTimer()
            }

            is HomeEvent.OnStopBuzzer -> state = state.copy(isTimerCountdownEnd = false)
        }
    }

    init {
        state = state.copy(isBluetoothEnable = bluetoothAdapter?.isEnabled == true)
    }

    private fun setTimeMill() {

        val userInputHour = TimeUnit.HOURS.toMillis(0)
        val userInputMinute = TimeUnit.MINUTES.toMillis(formatNumber(state.minutes).toLong())
        val userInputSecond = TimeUnit.SECONDS.toMillis(formatNumber(state.secondes).toLong())

        totalTimeMillis = userInputHour + userInputMinute + userInputSecond

        timeLeft = totalTimeMillis
    }

    private fun formatNumber(input: String): String {
        return if (input.startsWith("0") || (input.toIntOrNull() ?: 0) >= 9) {
            input
        } else {
            "0$input"
        }
    }

    private fun startCountDownTimer() = viewModelScope.launch {
        if (state.minutes.isNotEmpty() && state.secondes.isNotEmpty()) {
            state =
                state.copy(
                    isPlaying = true, isTimerBottomSheetOpen = false,
                    timerStatus = TimerStatus.Start, isDevicePower = true
                )

            setTimeMill()

            countDownTimer = object : CountDownTimer(timeLeft, countDownInterval) {
                override fun onTick(currentTimeLeft: Long) {
                    state = state.copy(timerText = currentTimeLeft.timeFormat())
                    timeLeft = currentTimeLeft
                }

                override fun onFinish() {
                    state = state.copy(
                        timerText = totalTimeMillis.timeFormat(),
                        timerStatus = TimerStatus.Stop,
                        isPlaying = false,
                        isTimerCountdownEnd = true
                    )
                    notificationService.showNotification("Temps écoulé")

                    viewModelScope.launch {
                        delay(1500)
                        state = state.copy(timerStatus = TimerStatus.Idle)
                    }
                }

            }.start()
        }

    }

    private fun runDevice() {
        val temp = if (state.isDevicePower) 0f else 0.83f

        state = state.copy(isDevicePower = !state.isDevicePower, temperature = temp)
        notificationService.showNotification("État du device : ${if (state.isDevicePower) "Allumé" else "Éteint"}")
    }

    private fun stopCountDownTimer() = viewModelScope.launch {
        state = state.copy(isPlaying = false)
        countDownTimer?.cancel()
    }

    private fun resetCountDownTimer() = viewModelScope.launch {
        countDownTimer?.cancel()
        state = state.copy(timerText = totalTimeMillis.timeFormat())
        timeLeft = totalTimeMillis
    }

}