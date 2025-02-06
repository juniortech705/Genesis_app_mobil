package cd.zgeniuscoders.genesis.remote_controle.data.mappers

import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothMessage

fun String.toBluetoothMessage(): BluetoothMessage {
    val message = substringAfter("#")
    return BluetoothMessage(message)
}

fun BluetoothMessage.toByteArray(): ByteArray {
    return message.encodeToByteArray()
}