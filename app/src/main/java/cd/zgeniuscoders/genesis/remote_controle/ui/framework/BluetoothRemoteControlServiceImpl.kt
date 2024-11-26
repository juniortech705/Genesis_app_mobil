package cd.zgeniuscoders.genesis.remote_controle.ui.framework

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothDevice
import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothDeviceModel
import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothMessage
import cd.zgeniuscoders.genesis.remote_controle.domain.services.BluetoothDataTransferService
import cd.zgeniuscoders.genesis.remote_controle.domain.services.ConnectionResult
import cd.zgeniuscoders.genesis.remote_controle.domain.services.RemoteControlService
import cd.zgeniuscoders.genesis.remote_controle.ui.framework.bluetooth.BluetoothStateReceiver
import cd.zgeniuscoders.genesis.remote_controle.ui.framework.bluetooth.FoundDeviceReceiver
import cd.zgeniuscoders.genesis.remote_controle.ui.framework.extensions.toBluetoothDeviceModel
import cd.zgeniuscoders.genesis.remote_controle.ui.framework.extensions.toByteArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.UUID

@SuppressLint("MissingPermission")
class BluetoothRemoteControlServiceImpl(
    private val context: Context
) : RemoteControlService {

    private val bluetoothManager by lazy {
        context.getSystemService(BluetoothManager::class.java)
    }
    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    private var dataTransferService: BluetoothDataTransferService? = null
    private var currentClientSocket: BluetoothSocket? = null

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

    private val foundDeviceReceiver = FoundDeviceReceiver { device ->

        _scannedDevices.update { devices ->
            run {
                val newDevice = device.toBluetoothDeviceModel()
                if (newDevice in devices) devices else devices + newDevice
            }
        }

    }

    private val bluetoothStateReceiver = BluetoothStateReceiver { isConnected, bluetoothDevice ->
        if (bluetoothAdapter?.bondedDevices?.contains(bluetoothDevice) == true) {
            _isConnected.update { isConnected }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                _errors.emit("Can't connect to a non-paired device.")
            }
        }
    }

    init {
        updatePairedDevices()
        context.registerReceiver(
            bluetoothStateReceiver,
            IntentFilter().apply {
                addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)
                addAction(android.bluetooth.BluetoothDevice.ACTION_ACL_CONNECTED)
                addAction(android.bluetooth.BluetoothDevice.ACTION_ACL_DISCONNECTED)
            }
        )
    }

    override suspend fun powerOn() {
        trySendMessage("ON")
    }

    override suspend fun powerOff() {
        trySendMessage("OFF")
    }

    override suspend fun buzzer() {
        trySendMessage("tone_on")
    }

    override suspend fun powerUp() {
        trySendMessage("power_up")
    }

    override suspend fun powerDown() {
        trySendMessage("power_down")
    }

    override fun startDiscovery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
            return
        }

        context.registerReceiver(
            foundDeviceReceiver,
            IntentFilter(android.bluetooth.BluetoothDevice.ACTION_FOUND)
        )

        bluetoothAdapter?.startDiscovery()

        updatePairedDevices()

    }

    override fun stopDiscovery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
            return
        }

        bluetoothAdapter?.cancelDiscovery()
    }

    override fun connectToDevice(device: BluetoothDevice): Flow<ConnectionResult> {
        return flow {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
                throw SecurityException("No BLUETOOTH_CONNECT permission")
            }

            currentClientSocket = bluetoothAdapter
                ?.getRemoteDevice(device.address)
                ?.createRfcommSocketToServiceRecord(
                    UUID.fromString(SERVICE_UUID)
                )

            stopDiscovery()

            currentClientSocket?.let { socket ->
                try {
                    socket.connect()
                    emit(ConnectionResult.ConnectionEstablished)

                    checkAuthorization()

                    BluetoothDataTransferService(socket).also {
                        dataTransferService = it
                        emitAll(
                            it.listenForIncomingMessages()
                                .map { ConnectionResult.TransferSucceeded(it) }
                        )
                    }
                } catch (e: IOException) {
                    socket.close()
                    currentClientSocket = null
                    e.printStackTrace()
                    emit(ConnectionResult.Error("Connection was interrupted"))
                }
            }
        }.onCompletion {
            closeConnection()
        }.flowOn(Dispatchers.IO)
    }

    override fun closeConnection() {
        currentClientSocket?.close()
        currentClientSocket = null
    }

    override suspend fun checkAuthorization() {
        trySendMessage("authorization: 11230")
    }

    override suspend fun getTemperature(): String {

        val regex = """([-+]?\d+\.\d{2})\s?°C""".toRegex()
        var temperature = "0"
        var messages: List<BluetoothMessage> = emptyList()

        val bluetoothMessage = trySendMessage("message")

        if (bluetoothMessage != null) {
            messages = messages + bluetoothMessage
        }

        messages.forEach { message ->

            val matchResult = regex.find(message.message)
            if (matchResult != null) {
                temperature = matchResult.groups[1]?.value.toString()
            }

        }

        return temperature
    }

    override fun release() {
        context.unregisterReceiver(foundDeviceReceiver)
        context.unregisterReceiver(bluetoothStateReceiver)
        closeConnection()
    }

    private fun updatePairedDevices() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
            return
        }

        bluetoothAdapter
            ?.bondedDevices
            ?.map { it.toBluetoothDeviceModel() }
            ?.also { devices ->
                _pairedDevices.update { devices }
            }
    }

    private suspend fun trySendMessage(message: String): BluetoothMessage? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
            return null
        }

        if (dataTransferService == null) {
            return null
        }

        val bluetoothMessage = BluetoothMessage(message = message)

        dataTransferService?.sendMessage(bluetoothMessage.toByteArray())

        return bluetoothMessage
    }

    private fun hasPermission(permission: String): Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        const val SERVICE_UUID = "00001101-0000-1000-8000-00805F9B34FB"
    }

}