package cd.zgeniuscoders.genesis.authentication.ui.views.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.LoginRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.getToken
import cd.zgeniuscoders.genesis.authentication.domain.usecases.UserInteractor
import cd.zgeniuscoders.genesis.authentication.ui.framework.SessionService
import cd.zgeniuscoders.genesis.common.utility.Resource
import cd.zgeniuscoders.genesis.common.utility.UserSettings
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userInteractor: UserInteractor,
    private val sessionService: SessionService
) : ViewModel() {

    var state by mutableStateOf(LoginState())

    fun onTriggerEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChange -> state = state.copy(email = event.value)
            is LoginEvent.PasswordChange -> state = state.copy(password = event.value)
            LoginEvent.Login -> login()
        }
    }

    private fun validate() {
        viewModelScope.launch {

            state = state.copy(isLoading = true)

            if (state.email.isEmpty() || state.password.isEmpty()) {
                state = this@LoginViewModel.state.copy(
                    error = "Veuillez remplir tous les champs",
                    isLoading = false,
                    hasFormError = true,
                )
                return@launch
            } else {
                state = state.copy(isLoading = false, isValidated = true)
            }

        }


    }

    fun login() {

        validate()

        if (state.isValidated) {
            viewModelScope.launch {

                state = state.copy(isLoading = true)
                val loginRequest = LoginRequest(state.email, state.password)
                val res = userInteractor.loginUseCase.run(loginRequest)

                state = when (res) {
                    is Resource.Error -> {
                        state.copy(
                            isValidated = false,
                            isLoading = false,
                            error = res.message,
                            hasFormError = true
                        )
                    }

                    is Resource.Success -> {
                        Log.i("success", "success")
                        sessionService.addUserToSession(
                            UserSettings(
                                isLogged = true,
                                token = res.data?.getToken()
                            )
                        )
                        state.copy(isLogged = true, isValidated = false, isLoading = false)
                    }
                }

            }
        }


    }

}


