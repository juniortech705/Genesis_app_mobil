package cd.zgeniuscoders.genesis.authentication.ui.presentation.forgot

data class ForgotPasswordState(
    var email: String = "",
    var isLoading: Boolean = false,
    var isValidated: Boolean=false,
    var hasFormError: Boolean=false,
    var error: String = "",
    var isLogged: Boolean = false,
)