package cd.zgeniuscoders.genesis.authentication.domain.usecases

import cd.zgeniuscoders.genesis.core.domain.utils.Resource
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.LoginRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.LoginDto
import cd.zgeniuscoders.genesis.authentication.domain.services.AuthenticationService

class LoginUseCase(private val userServices: AuthenticationService) {

    suspend fun run(loginRequest: LoginRequest): Resource<LoginDto> {
        return userServices.login(loginRequest)
    }

}