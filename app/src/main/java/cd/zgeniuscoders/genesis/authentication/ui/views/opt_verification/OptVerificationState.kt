package cd.zgeniuscoders.genesis.authentication.ui.views.opt_verification

data class OptVerificationState(
    var code: String = "",
    var error: String = "",
    var hasFormError: Boolean = false,
    var isLogged: Boolean = false,
    var isLoading: Boolean = false
)
