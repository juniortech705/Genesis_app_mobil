package cd.zgeniuscoders.genesis.remote_controle.ui.framework

import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothDevice
import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothDeviceModel
import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothMessage
import cd.zgeniuscoders.genesis.remote_controle.domain.services.ConnectionResult
import cd.zgeniuscoders.genesis.remote_controle.domain.services.RemoteControlService
import cd.zgeniuscoders.genesis.remote_controle.ui.framework.extensions.toBluetoothDeviceModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

class FakeBluetoothRemoteControlService : RemoteControlService {

    private val _isConnected = MutableStateFlow(false)
    override val isConnected: StateFlow<Boolean>
        get() = _isConnected.asStateFlow()

    private val _scannedDevices = MutableStateFlow<List<BluetoothDeviceModel>>(emptyList())
    override val scannedDevices: StateFlow<List<BluetoothDeviceModel>>
        get() = _scannedDevices.asStateFlow()

    private val _pairedDevices = MutableStateFlow<List<BluetoothDeviceModel>>(emptyList())
    override val pairedDevices: StateFlow<List<BluetoothDeviceModel>>
        get() = _pairedDevices.asStateFlow()

    private val _errors = MutableSharedFlow<String>()
    override val errors: SharedFlow<String>
        get() = _errors.asSharedFlow()

    override suspend fun powerOn() {

    }

    override suspend fun powerOff() {

    }

    override suspend fun buzzer() {

    }

    override suspend fun powerUp() {

    }

    override suspend fun powerDown() {

    }

    override suspend fun checkAuthorization() {
        TODO("Not yet implemented")
    }

    override fun startDiscovery() {
        _scannedDevices.update { devices ->
            run {
                listOf(
                    BluetoothDeviceModel("Genesis-Bt", "02:dj:62:v5:sx"),
                    BluetoothDeviceModel("Alpha-Bt", "01:ab:23:cd:45"),
                    BluetoothDeviceModel("Omega-Bt", "03:ef:56:gh:78"),
                    BluetoothDeviceModel("Delta-Bt", "04:ij:89:kl:01"),
                    BluetoothDeviceModel("Zeta-Bt", "05:mn:12:op:34"),
                    BluetoothDeviceModel("Theta-Bt", "06:qr:56:st:78"),
                    BluetoothDeviceModel("Iota-Bt", "07:uv:90:wx:12"),
                    BluetoothDeviceModel("Kappa-Bt", "08:yz:34:ab:56"),
                    BluetoothDeviceModel("Lambda-Bt", "09:cd:78:ef:90"),
                    BluetoothDeviceModel("Mu-Bt", "10:gh:12:ij:34")
                )
            }
        }
        _pairedDevices.update { devices ->
            run {
                listOf(
                    BluetoothDeviceModel("Genesis-Bt", "02:dj:62:v5:sx"),
                    BluetoothDeviceModel("Alpha-Bt", "01:ab:23:cd:45"),
                    BluetoothDeviceModel("Omega-Bt", "03:ef:56:gh:78"),
                    BluetoothDeviceModel("Delta-Bt", "04:ij:89:kl:01"),
                    BluetoothDeviceModel("Zeta-Bt", "05:mn:12:op:34"),
                    BluetoothDeviceModel("Theta-Bt", "06:qr:56:st:78"),
                    BluetoothDeviceModel("Iota-Bt", "07:uv:90:wx:12"),
                    BluetoothDeviceModel("Kappa-Bt", "08:yz:34:ab:56"),
                    BluetoothDeviceModel("Lambda-Bt", "09:cd:78:ef:90"),
                    BluetoothDeviceModel("Mu-Bt", "10:gh:12:ij:34")
                )
            }
        }
    }

    override fun stopDiscovery() {

    }

    override fun connectToDevice(device: BluetoothDevice): Flow<ConnectionResult> {
        return flow {
            delay(5000)
            emit(ConnectionResult.ConnectionEstablished)
        }
    }

    override fun closeConnection() {

    }

    override suspend fun getTemperature(): String {
        delay(5000)
        val randomNumber = (0..80).random()
        return randomNumber.toString()
    }

    override fun release() {

    }
}