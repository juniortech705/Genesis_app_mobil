package cd.zgeniuscoders.genesis.authentication.domain.usecases

import cd.zgeniuscoders.genesis.common.utility.Resource
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.ForgotPasswordRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.AuthenticationDto
import cd.zgeniuscoders.genesis.authentication.domain.services.AuthenticationService


class ForgotPasswordUseCase(
    private val userService: AuthenticationService
) {

    suspend fun run(request: ForgotPasswordRequest): Resource<AuthenticationDto> {
        return userService.forgotPassword(request)
    }

}