package cd.zgeniuscoders.genesis.authentication.ui.views.forgot

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cd.zgeniuscoders.genesis.authentication.domain.usecases.UserInteractor

class ForgotPasswordViewModel(
    val userInteractor: UserInteractor
) : ViewModel() {

    fun onTriggerEvent(event: ForgotPasswordEvent) {
        when (event) {
            is ForgotPasswordEvent.EmailChange -> state = state.copy(email = event.value)
        }
    }

    private fun validate(){
        if(state.email.isEmpty()){
            state = state.copy(hasFormError = true, isValidated = false)
        }
    }
    fun sendEmail() {
        validate()
    }

    var state by mutableStateOf(ForgotPasswordState())

}
