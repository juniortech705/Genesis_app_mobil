package cd.zgeniuscoders.genesis.authentication.ui.views.forgot

sealed class ForgotPasswordEvent {
    data class EmailChange(val value: String) : ForgotPasswordEvent()
}