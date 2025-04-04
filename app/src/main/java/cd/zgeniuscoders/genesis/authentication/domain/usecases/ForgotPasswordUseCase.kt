package cd.zgeniuscoders.genesis.authentication.domain.usecases

import cd.zgeniuscoders.genesis.core.domain.utils.Resource
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.ForgotPasswordRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.ForgotPasswordDto
import cd.zgeniuscoders.genesis.authentication.domain.services.AuthenticationService


class ForgotPasswordUseCase(
    private val userService: AuthenticationService
) {

    suspend fun run(request: ForgotPasswordRequest): Resource<ForgotPasswordDto> {
        return userService.forgotPassword(request)
    }

}