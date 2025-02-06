package cd.zgeniuscoders.genesis.authentication.ui.presentation.register

data class RegisterState(
    var email: String = "",
    var username: String = "",
    var password: String = "",
    var confirmPassword: String = "",
    var isLoading: Boolean = false,
    var error: String? = null,
    var isValidated: Boolean = false,
    var hasFormError: Boolean = false,
    var isLogged: Boolean = false,
    var errors: HashMap<String, List<String>>? = null
)