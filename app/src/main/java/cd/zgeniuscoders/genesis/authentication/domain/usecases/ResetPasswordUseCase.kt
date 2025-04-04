package cd.zgeniuscoders.genesis.authentication.domain.usecases

import cd.zgeniuscoders.genesis.core.domain.utils.Resource
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.ResetPasswordRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.ResetPasswordDto
import cd.zgeniuscoders.genesis.authentication.domain.services.AuthenticationService


class ResetPasswordUseCase(
    private val userService: AuthenticationService
) {

    suspend fun run(request: ResetPasswordRequest): Resource<ResetPasswordDto> {
        return userService.resetPassword(request)
    }

}