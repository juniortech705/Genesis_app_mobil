package cd.zgeniuscoders.genesis.authentication.ui.presentation.login

data class LoginState(
    var email: String="",
    var password: String="",
    var emailError: String="",
    var isLoading: Boolean=false,
    var error: String?=null,
    var isValidated: Boolean=false,
    var hasFormError: Boolean=false,
    var isLogged: Boolean=false,
)