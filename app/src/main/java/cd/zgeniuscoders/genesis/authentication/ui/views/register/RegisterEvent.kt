package cd.zgeniuscoders.genesis.authentication.ui.views.register

sealed class RegisterEvent {
    data class EmailChange(val value: String) : RegisterEvent()
    data class UsernameChange(val value: String) : RegisterEvent()
    data class PasswordChange(val value: String) : RegisterEvent()
    data class ConfirmPasswordChange(val value: String) : RegisterEvent()
    data object OnRegister: RegisterEvent()
}