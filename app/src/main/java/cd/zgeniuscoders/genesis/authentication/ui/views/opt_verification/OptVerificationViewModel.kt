package cd.zgeniuscoders.genesis.authentication.ui.views.opt_verification

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.OptVerificationRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.getToken
import cd.zgeniuscoders.genesis.authentication.domain.usecases.UserInteractor
import cd.zgeniuscoders.genesis.authentication.ui.framework.SessionService
import cd.zgeniuscoders.genesis.common.utility.Resource
import cd.zgeniuscoders.genesis.common.utility.UserSettings
import cd.zgeniuscoders.speedshop.authentication.domain.mappers.gtError
import cd.zgeniuscoders.speedshop.authentication.domain.mappers.gtOptError
import kotlinx.coroutines.launch

class OptVerificationViewModel(
    val authenticationInteractor: UserInteractor,
    private val sessionService: SessionService
) : ViewModel() {

    var state by mutableStateOf(OptVerificationState())

    fun onTriggerEvent(event: OptVerificationEvent) {
        when (event) {
            OptVerificationEvent.HandlerSendCod -> sendCode()
            is OptVerificationEvent.OnCodeChange -> state = state.copy(code = event.code)
        }
    }

    private fun validate() {
        state = state.copy(isLoading = true)
        if (state.code.isEmpty() || state.code.length < 6) {
            state = state.copy(
                error = "Veuillez saisir le code a chiffres",
                hasFormError = true,
                isLoading = false
            )
        }
    }

    private fun sendCode() {
        validate()
        viewModelScope.launch {
            if (!state.hasFormError) {
                val res =
                    authenticationInteractor.verifyOptUsecase.run(OptVerificationRequest(state.code))
                state = when (res) {
                    is Resource.Error -> {
                        Log.i("GENESIS", res.data.toString())
                        state.copy(error = res.data?.gtError() ?: "")
                    }

                    is Resource.Success -> {
                        sessionService.addUserToSession(
                            UserSettings(
                                isLogged = true,
                                token = res.data!!.otpToken
                            )
                        )
                        state.copy(hasFormError = false, isLogged = true)
                    }
                }

                state = state.copy(isLoading = false)
            }
        }
    }

}