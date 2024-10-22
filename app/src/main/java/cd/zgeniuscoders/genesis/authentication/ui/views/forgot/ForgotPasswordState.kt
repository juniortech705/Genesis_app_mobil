package cd.zgeniuscoders.genesis.authentication.ui.views.forgot

data class ForgotPasswordState(
    var email: String = "",
    var emailError: String="",
    var isLoading: Boolean = false,
    var isValidated: Boolean=false,
    var hasFormError: Boolean=false
)