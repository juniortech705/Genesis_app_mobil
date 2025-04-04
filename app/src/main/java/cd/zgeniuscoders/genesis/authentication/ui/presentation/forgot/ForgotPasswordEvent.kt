package cd.zgeniuscoders.genesis.authentication.ui.presentation.forgot

sealed class ForgotPasswordEvent {
    data class EmailChange(val value: String) : ForgotPasswordEvent()
    data object handlerSendEmail: ForgotPasswordEvent()
}