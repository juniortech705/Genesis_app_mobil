package cd.zgeniuscoders.genesis.remote_controle.presentation.addDevice

sealed class AddDeviceEvent {

    data object OnRefreshButtonPressed: cd.zgeniuscoders.genesis.remote_controle.presentation.addDevice.AddDeviceEvent()
    data object OnStopButtonPressed: cd.zgeniuscoders.genesis.remote_controle.presentation.addDevice.AddDeviceEvent()

}