package cd.zgeniuscoders.genesis.authentication.domain.usecases

import cd.zgeniuscoders.genesis.common.utility.Resource
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.ResetPasswordRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.AuthenticationDto
import cd.zgeniuscoders.genesis.authentication.domain.services.AuthenticationService


class ResetPasswordUseCase(
    private val userService: AuthenticationService
) {

    suspend fun run(request: ResetPasswordRequest): Resource<AuthenticationDto> {
        return userService.resetPassword(request)
    }

}