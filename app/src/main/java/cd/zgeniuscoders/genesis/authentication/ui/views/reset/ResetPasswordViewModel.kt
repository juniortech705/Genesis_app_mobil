package cd.zgeniuscoders.genesis.authentication.ui.views.reset

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.genesis.authentication.domain.usecases.UserInteractor
import kotlinx.coroutines.launch


class ResetPasswordViewModel(
    userInteractor: UserInteractor
) : ViewModel(){

    var state by mutableStateOf(ResetPasswordState())

    fun onTriggerEvent(event: ResetPasswordEvent) {
        when (event) {
            is ResetPasswordEvent.PasswordChange -> state = state.copy(password = event.value)
            is ResetPasswordEvent.ConfirmPasswordChange -> state =
                state.copy(confirmPassword = event.value)
        }
    }

    private fun validate() {
        viewModelScope.launch {

            if (state.password.length < 6) {
                state = state.copy(
                    hasFormError = true,
                    isLoading = false,
                    error = "Le mot de passe doit avoir plus de 6 caractères"
                )
                return@launch
            }

            if (state.password != state.confirmPassword) {
                state = state.copy(
                    hasFormError = true,
                    isLoading = false,
                    error = "Le mot de passe ne correspond pas"
                )
                return@launch
            }

            state = state.copy(isValidated = true, isLoading = false, hasFormError = false)
        }

    }

    fun resestPassword() {
        validate()
        if(state.isValidated){
            // do something
            state = state.copy(isLogged = true)
        }
    }

}
