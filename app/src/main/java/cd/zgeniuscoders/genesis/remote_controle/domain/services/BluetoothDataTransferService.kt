package cd.zgeniuscoders.genesis.remote_controle.domain.services

import android.bluetooth.BluetoothSocket
import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothMessage
import cd.zgeniuscoders.genesis.remote_controle.ui.framework.extensions.toBluetoothMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.IOException


class BluetoothDataTransferService(
    private val socket: BluetoothSocket
) {

    fun listenForIncomingMessages(): Flow<BluetoothMessage> {
        return flow {
            if (!socket.isConnected) {
                return@flow
            }
            val buffer = ByteArray(1024)
            while (true) {
                val byteCount = try {
                    socket.inputStream.read(buffer)
                } catch (e: IOException) {
                    throw IOException("can't read data from socket")
                }
                emit(
                    buffer.decodeToString(
                        endIndex = byteCount
                    ).toBluetoothMessage()
                )
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun sendMessage(bytes: ByteArray): Boolean{
        return withContext(Dispatchers.IO){
            try{
                socket.outputStream.write(bytes)
            }catch (e:IOException){
                e.printStackTrace()
                return@withContext false
            }

            true
        }
    }

}