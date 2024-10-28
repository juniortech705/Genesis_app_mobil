package cd.zgeniuscoders.genesis.authentication.ui.views.forgot

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.ForgotPasswordRequest
import cd.zgeniuscoders.genesis.authentication.domain.usecases.UserInteractor
import cd.zgeniuscoders.genesis.common.utility.Resource
import cd.zgeniuscoders.speedshop.authentication.domain.mappers.getEmailError
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    val userInteractor: UserInteractor
) : ViewModel() {

    fun onTriggerEvent(event: ForgotPasswordEvent) {
        when (event) {
            is ForgotPasswordEvent.EmailChange -> state = state.copy(email = event.value)
            ForgotPasswordEvent.handlerSendEmail -> sendEmail()
        }
    }

    private fun validate() {

        state = if (!isValidEmail(state.email)) {
            this@ForgotPasswordViewModel.state.copy(
                error = "L'adresse email saisie n'est pas valide. Veuillez vérifier et réessayer.",
                isLoading = false,
                hasFormError = true,
            )
        } else {
            state.copy(hasFormError = false)
        }
    }

    fun sendEmail() {
        validate()

        state = state.copy(isLoading = true)

        viewModelScope.launch {
            if (!state.hasFormError) {

                val res =
                    userInteractor.forgotPasswordUseCase.run(ForgotPasswordRequest(state.email))
                state = when (res) {
                    is Resource.Error -> {
                        state.copy(isLoading = false, error = res.data!!.getEmailError())
                    }

                    is Resource.Success -> {
                        state.copy(isLogged = true, isLoading = false)
                    }
                }
            } else {
                state = state.copy(isLoading = false)
            }
        }
    }

    var state by mutableStateOf(ForgotPasswordState())

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        return emailRegex.matches(email)
    }

}
