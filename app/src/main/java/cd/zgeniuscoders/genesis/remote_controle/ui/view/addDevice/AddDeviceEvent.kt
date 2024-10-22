package cd.zgeniuscoders.genesis.remote_controle.ui.view.addDevice

sealed class AddDeviceEvent {

    data object OnRefreshButtonPressed: AddDeviceEvent()
    data object OnStopButtonPressed: AddDeviceEvent()

}