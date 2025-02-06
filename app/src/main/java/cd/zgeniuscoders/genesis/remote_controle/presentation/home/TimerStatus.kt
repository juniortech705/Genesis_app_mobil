package cd.zgeniuscoders.genesis.remote_controle.presentation.home

sealed class TimerStatus {
    data object Idle: TimerStatus();
    data object Start: TimerStatus();
    data object Stop: TimerStatus();
}