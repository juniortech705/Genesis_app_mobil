package cd.zgeniuscoders.genesis.remote_controle.domain.services

interface RemoteControlService {

    suspend fun powerOn()

    suspend fun buzzer()

    suspend fun startDiscovery()

    suspend fun stopDiscovery()

    suspend fun connectToDevice()

}