package cd.zgeniuscoders.genesis.common.ui.views.main

sealed interface ShareEvent {

    data object OnLogout: ShareEvent

}