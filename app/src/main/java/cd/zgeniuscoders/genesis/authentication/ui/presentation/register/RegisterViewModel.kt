package cd.zgeniuscoders.genesis.authentication.ui.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.RegisterRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.getToken
import cd.zgeniuscoders.genesis.authentication.domain.usecases.UserInteractor
import cd.zgeniuscoders.genesis.core.data.services.SessionService
import cd.zgeniuscoders.genesis.core.domain.utils.Resource
import cd.zgeniuscoders.genesis.core.domain.utils.UserSettings
import cd.zgeniuscoders.speedshop.authentication.domain.mappers.toAuthenticationErrorHashMap
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userInteractor: UserInteractor,
    private val sessionService: SessionService
) : ViewModel() {

    var state by mutableStateOf(RegisterState())

    fun onTriggerEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.ConfirmPasswordChange -> state =
                state.copy(confirmPassword = event.value)

            is RegisterEvent.EmailChange -> state = state.copy(email = event.value)
            is RegisterEvent.PasswordChange -> state = state.copy(password = event.value)
            is RegisterEvent.UsernameChange -> state = state.copy(username = event.value)
            RegisterEvent.OnRegister -> register()
        }
    }


    private fun validate() {
        if (state.username.isEmpty() || state.password.isEmpty() || state.email.isEmpty()) {
            state = this@RegisterViewModel.state.copy(
                error = "Veuillez remplir tous les champs",
                isLoading = false,
                hasFormError = true,
            )
        } else if(!isValidUsername(state.username)){
            state = this@RegisterViewModel.state.copy(
                error = "Le nom d'utilisateur saisie n'est pas valide.",
                isLoading = false,
                hasFormError = true,
            )
        }
        else if (!isValidEmail(state.email)) {
            state = this@RegisterViewModel.state.copy(
                error = "L'adresse email saisie n'est pas valide. Veuillez vérifier et réessayer.",
                isLoading = false,
                hasFormError = true,
            )
        } else if (state.password.length < 6) {
            state = this@RegisterViewModel.state.copy(
                error = "Le mot de passe doit avoir plus de 6 caractères",
                isLoading = false,
                hasFormError = true,
            )
        } else if (state.password != state.confirmPassword) {
            state = this@RegisterViewModel.state.copy(
                error = "Le mot de passe ne correspond pas",
                isLoading = false,
                hasFormError = true,
            )
        } else {
            state = state.copy(hasFormError = false)
        }
    }

    fun register() {
        validate()

        viewModelScope.launch {

            state = state.copy(isLoading = true)

            if (!state.hasFormError) {

                val request = RegisterRequest(
                    username = state.username,
                    password = state.password,
                    email = state.email,
                    confirmPassword = state.confirmPassword
                )
                when (val res = userInteractor.registerUseCase.run(request)) {
                    is Resource.Error -> {
                        res.data.let {

                            state = state.copy(
                                isValidated = false,
                                isLoading = false,
                                error = res.message,
                                hasFormError = true,
                                errors = it?.toAuthenticationErrorHashMap()
                            )

                        }

                    }

                    is Resource.Success -> {
                        sessionService.addUserToSession(
                            UserSettings(
                                isLogged = true,
                                token = res.data?.getToken()
                            )
                        )
                        state = state.copy(isLogged = true, isValidated = false, isLoading = false)
                    }
                }
            }

        }


    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        return emailRegex.matches(email)
    }

    private fun isValidUsername(username: String): Boolean {
        // Règles : 3-20 caractères, commence par une lettre, suivi de lettres, chiffres ou underscores
        val usernameRegex = "^[A-Za-z][A-Za-z0-9_]{2,19}$".toRegex()
        return usernameRegex.matches(username)
    }

}