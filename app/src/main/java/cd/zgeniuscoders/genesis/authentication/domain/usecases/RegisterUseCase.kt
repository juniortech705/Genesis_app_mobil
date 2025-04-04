package cd.zgeniuscoders.genesis.authentication.domain.usecases

import cd.zgeniuscoders.genesis.core.domain.utils.Resource
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.RegisterRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.AuthenticationDto
import cd.zgeniuscoders.genesis.authentication.domain.services.AuthenticationService

class RegisterUseCase(
    private val userServices: AuthenticationService
) {

    suspend fun run(registerRequest: RegisterRequest): Resource<AuthenticationDto> {
        return userServices.register(registerRequest)
    }

}