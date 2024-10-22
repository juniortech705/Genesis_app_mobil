package cd.zgeniuscoders.genesis.authentication.ui.views.reset

data class ResetPasswordState(
    var password: String = "",
    var confirmPassword: String = "",
    var isLoading: Boolean = false,
    var error: String? = null,
    var successMessage: String? = null,
    var isValidated: Boolean=false,
    var hasFormError: Boolean=false
)

