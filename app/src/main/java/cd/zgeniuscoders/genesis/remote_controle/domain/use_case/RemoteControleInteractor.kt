package cd.zgeniuscoders.genesis.remote_controle.domain.use_case

import cd.zgeniuscoders.genesis.remote_controle.domain.services.RemoteControlService

class RemoteControleInteractor(
    val buzzer: Buzzer,
    val connectToDevice: ConnectToDevice,
    val stopDiscovery: StopDiscovery,
    val disconnectToDevice: DisconnectToDevice,
    val startDiscovery: StartDiscovery,
    val powerOn: PowerOn,
    val isConnected: IsConnected,
    val errors: RemoteControlError,
    val scannedDevices: ScannedDevices,
    val pairedDevices: PairedDevices,
    val powerOff: PowerOff,
    val release: Release
) {

    companion object {
        fun build(service: RemoteControlService): RemoteControleInteractor {
            return RemoteControleInteractor(
                Buzzer(service),
                ConnectToDevice(service),
                StopDiscovery(service),
                DisconnectToDevice(service),
                StartDiscovery(service),
                PowerOn(service),
                IsConnected(service),
                RemoteControlError(service),
                ScannedDevices(service),
                PairedDevices(service),
                PowerOff(service),
                Release(service)
            )
        }
    }

}