package cd.zgeniuscoders.genesis.remote_controle.ui.framework.bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.util.UUID

class ConnectThread(device: BluetoothDevice) : Thread() {

    private val uuid = "00001101-0000-10000-8000-00805F9B34F8"
    private var socket: BluetoothSocket? = null

    init {
        try {
            socket = device.createRfcommSocketToServiceRecord(UUID.fromString(uuid))
        } catch (e: IOException) {
        } catch (se: SecurityException) {

        }
    }

    override fun run() {
        try {
            socket?.connect()
            readMessage()
        } catch (e: IOException) {
            Log.e("BLUETOOTH_ERREUR", e.message.toString())
        } catch (se: SecurityException) {
            Log.e("BLUETOOTH_ERREUR", se.message.toString())
        }
    }

    private fun readMessage() {
        val buffer = ByteArray(256)
        while (true) {
            try {
                val length = socket?.inputStream?.read(buffer)
                val messages = String(buffer, 0, length ?: 0)
            } catch (e: IOException) {
                Log.e("BLUETOOTH_ERREUR", e.message.toString())
                break
            }
        }
    }

    fun closeConnection() {
        try {
            socket?.close()
        } catch (e: IOException) {
            Log.e("BLUETOOTH_ERREUR", e.message.toString())
        }
    }
}