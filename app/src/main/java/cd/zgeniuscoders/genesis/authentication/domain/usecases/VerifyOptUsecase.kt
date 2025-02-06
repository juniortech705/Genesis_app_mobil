package cd.zgeniuscoders.genesis.authentication.domain.usecases

import cd.zgeniuscoders.genesis.authentication.domain.models.Request.OptVerificationRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.OptVerificationDto
import cd.zgeniuscoders.genesis.authentication.domain.services.AuthenticationService
import cd.zgeniuscoders.genesis.core.domain.utils.Resource

class VerifyOptUsecase(val service: AuthenticationService) {

    suspend fun run(request: OptVerificationRequest): Resource<OptVerificationDto> {
        return service.verifyOpt(request)
    }

}