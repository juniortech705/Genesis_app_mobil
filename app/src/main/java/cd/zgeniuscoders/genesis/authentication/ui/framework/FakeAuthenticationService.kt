package cd.zgeniuscoders.genesis.authentication.ui.framework

import cd.zgeniuscoders.genesis.authentication.domain.models.Request.ForgotPasswordRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.LoginRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.RegisterRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.ResetPasswordRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.AuthenticationDto
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.LoginDataDto
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.LoginDto
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.LogoutDto
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.RegisterDtoData
import cd.zgeniuscoders.genesis.authentication.domain.services.AuthenticationService
import cd.zgeniuscoders.genesis.common.utility.Resource

class FakeAuthenticationService : AuthenticationService {
    override suspend fun login(request: LoginRequest): Resource<LoginDto> {
        return Resource.Success(
            data = LoginDto(data = LoginDataDto(token = "00000"))
        )
    }

    override suspend fun register(request: RegisterRequest): Resource<AuthenticationDto> {
        return Resource.Success(
            data = AuthenticationDto(data = RegisterDtoData(token = "0000000"))
        )
    }

    override suspend fun logout(): Resource<LogoutDto> {
        return Resource.Success(
            data = LogoutDto()
        )
    }

    override suspend fun forgotPassword(request: ForgotPasswordRequest): Resource<AuthenticationDto> {
        return Resource.Success(
            data = AuthenticationDto(data = RegisterDtoData(token = "0000000"))
        )
    }

    override suspend fun resetPassword(request: ResetPasswordRequest): Resource<AuthenticationDto> {
        TODO("Not yet implemented")
    }
}