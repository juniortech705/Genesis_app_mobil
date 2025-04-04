package cd.zgeniuscoders.genesis.remote_controle.domain.services

import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothDevice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface RemoteControlService {

    val isConnected: StateFlow<Boolean>
    val scannedDevices: StateFlow<List<BluetoothDevice>>
    val pairedDevices: StateFlow<List<BluetoothDevice>>
    val errors: SharedFlow<String>

    suspend fun powerOn()

    suspend fun powerOff()

    suspend fun buzzer()

    suspend fun powerUp(value: String)

    suspend fun powerDown(value: String)

    suspend fun checkAuthorization()

    fun startDiscovery()

    fun stopDiscovery()

    fun connectToDevice(device: BluetoothDevice): Flow<ConnectionResult>

    fun closeConnection()

    suspend fun getTemperature(): String

    fun release()

}