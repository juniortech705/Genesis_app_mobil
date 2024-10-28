package cd.zgeniuscoders.genesis.authentication.ui.views.opt_verification

sealed interface OptVerificationEvent {

    data class OnCodeChange(val code: String): OptVerificationEvent

    data object HandlerSendCod: OptVerificationEvent

}