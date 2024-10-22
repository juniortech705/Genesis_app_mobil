package cd.zgeniuscoders.genesis.remote_controle.domain.services

import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothMessage

sealed interface ConnectionResult {
    object ConnectionEstablished: ConnectionResult
    data class TransferSucceeded(val message: BluetoothMessage): ConnectionResult
    data class Error(val message: String): ConnectionResult
}