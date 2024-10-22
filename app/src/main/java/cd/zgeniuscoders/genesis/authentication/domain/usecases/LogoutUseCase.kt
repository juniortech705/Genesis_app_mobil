package cd.zgeniuscoders.genesis.authentication.domain.usecases

import cd.zgeniuscoders.genesis.common.utility.Resource
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.LogoutDto
import cd.zgeniuscoders.genesis.authentication.domain.services.AuthenticationService

class LogoutUseCase(
    val service: AuthenticationService
) {

    suspend fun run(): Resource<LogoutDto> {
        return service.logout()
    }


}