package cd.zgeniuscoders.genesis.common.ui.views.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.genesis.authentication.domain.usecases.UserInteractor
import cd.zgeniuscoders.genesis.authentication.ui.framework.SessionService
import cd.zgeniuscoders.genesis.common.utility.Resource
import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothDeviceModel
import cd.zgeniuscoders.genesis.remote_controle.domain.services.BluetoothController
import cd.zgeniuscoders.genesis.remote_controle.domain.services.ConnectionResult
import cd.zgeniuscoders.genesis.remote_controle.domain.use_case.RemoteControleInteractor
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShareViewModel(
    private val remoteControleInteractor: RemoteControleInteractor,
    private val authenticationInteractor: UserInteractor,
    private val sessionService: SessionService
) : ViewModel() {

    private val _state = MutableStateFlow(ShareState())
    val state = combine(
        remoteControleInteractor.scannedDevices.run(),
        remoteControleInteractor.pairedDevices.run(),
        _state
    ) { scannedDevices, pairedDevices, state ->
        state.copy(
            scannedDevices = scannedDevices,
            pairedDevices = pairedDevices,
            messages = if (state.isConnected) state.messages else emptyList()
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    fun onTriggerEvent(event: ShareEvent) {
        when (event) {
            ShareEvent.OnLogout -> logout()
            ShareEvent.Buzzer -> buzzer()
            ShareEvent.PowerOff -> powerOn()
            ShareEvent.PowerOn -> powerOff()
            is ShareEvent.ConnectDevice -> connectToDevice(event.device)
            ShareEvent.PowerDown -> powerDown()
            ShareEvent.PowerUp -> powerUp()
        }
    }

    private var deviceConnectionJob: Job? = null

    init {
        remoteControleInteractor.isConnected.run().onEach { isConnected ->
            _state.update { it.copy(isConnected = isConnected) }
        }.launchIn(viewModelScope)

        remoteControleInteractor.errors.run().onEach { error ->
            _state.update {
                it.copy(
                    errorMessage = error
                )
            }
        }.launchIn(viewModelScope)
    }


    private fun connectToDevice(device: BluetoothDeviceModel) {
        _state.update { it.copy(isConnecting = true) }
        deviceConnectionJob = remoteControleInteractor
            .connectToDevice
            .run(device)
            .listen()
    }

    fun disconnectFromDevice() {
        deviceConnectionJob?.cancel()
        remoteControleInteractor.disconnectToDevice.run()
        _state.update {
            it.copy(
                isConnecting = false,
                isConnected = false
            )
        }
    }
//
//    fun sendMessage(message: String) {
//        viewModelScope.launch {
//            val bluetoothMessage = bluetoothController.trySendMessage(message)
//            Log.d("SEND TONE", "sendMessage: $bluetoothMessage")
//            if (bluetoothMessage != null) {
//                _state.update {
//                    it.copy(
//                        messages = it.messages + bluetoothMessage
//                    )
//                }
//            }
//        }
//    }

    fun startScan() {
        _state.update {
            it.copy(isScanning = true)
        }
        remoteControleInteractor.startDiscovery.run()
    }

    fun stopScan() {
        _state.update {
            it.copy(isScanning = false)
        }
        remoteControleInteractor.stopDiscovery.run()
    }

    private fun Flow<ConnectionResult>.listen(): Job {
        return onEach { result ->
            when (result) {
                ConnectionResult.ConnectionEstablished -> {
                    _state.update {
                        it.copy(
                            isConnected = true,
                            isConnecting = false,
                            errorMessage = null
                        )
                    }
                }

                is ConnectionResult.TransferSucceeded -> {
                    _state.update {
                        it.copy(
                            messages = it.messages + result.message
                        )
                    }
                }

                is ConnectionResult.Error -> {
                    _state.update {
                        it.copy(
                            isConnected = false,
                            isConnecting = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
            .catch { throwable ->
                remoteControleInteractor.disconnectToDevice.run()
                _state.update {
                    it.copy(
                        isConnected = false,
                        isConnecting = false,
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        remoteControleInteractor.release.run()
    }

    private fun logout() {
        viewModelScope.launch {
            val res = authenticationInteractor.logoutUseCase.run()
            when (res) {

                is Resource.Error -> {

                }

                is Resource.Success -> {
                    sessionService.clearUserPref()
                }
            }
        }
    }

    private fun powerOn() {
        viewModelScope.launch {
            remoteControleInteractor.powerOn.run()
        }
    }

    private fun powerOff() {
        viewModelScope.launch {
            remoteControleInteractor.powerOff.run()
        }
    }

    private fun buzzer() {
        viewModelScope.launch {
            remoteControleInteractor.buzzer.run()
        }
    }

    private fun powerUp() {
        viewModelScope.launch {
            remoteControleInteractor.powerUp.run()
        }
    }

    private fun powerDown() {
        viewModelScope.launch {
            remoteControleInteractor.powerDown.run()
        }
    }
}