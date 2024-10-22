package cd.zgeniuscoders.genesis.authentication.ui.views.reset

sealed class ResetPasswordEvent {
    //    data object ValidateForm : LoginEvent()
//
//    data object Submit : LoginEvent()
    data class PasswordChange(val value: String) : ResetPasswordEvent()
    data class ConfirmPasswordChange(val value: String) : ResetPasswordEvent()

}