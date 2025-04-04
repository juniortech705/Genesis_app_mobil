package cd.zgeniuscoders.genesis.authentication.ui.presentation.login

sealed class LoginEvent {
//    data object ValidateForm : LoginEvent()
//
//    data object Submit : LoginEvent()
    data class EmailChange(val value: String) : LoginEvent()
    data class PasswordChange(val value: String) : LoginEvent()
    data object Login: LoginEvent()
}
