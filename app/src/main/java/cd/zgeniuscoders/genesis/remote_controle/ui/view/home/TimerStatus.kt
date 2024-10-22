package cd.zgeniuscoders.genesis.remote_controle.ui.view.home

sealed class TimerStatus {
    data object Idle: TimerStatus();
    data object Start: TimerStatus();
    data object Stop: TimerStatus();
}