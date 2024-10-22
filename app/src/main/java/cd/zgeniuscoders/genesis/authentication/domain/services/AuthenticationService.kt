package cd.zgeniuscoders.genesis.authentication.domain.services

import cd.zgeniuscoders.genesis.common.utility.Resource
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.ForgotPasswordRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.LoginRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.RegisterRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.ResetPasswordRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.AuthenticationDto
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.LoginDto
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.LogoutDto

interface AuthenticationService {

    suspend fun login(request: LoginRequest): Resource<LoginDto>

    suspend fun register(request: RegisterRequest): Resource<AuthenticationDto>

    suspend fun logout(): Resource<LogoutDto>

    suspend fun forgotPassword(request: ForgotPasswordRequest): Resource<AuthenticationDto>

    suspend fun resetPassword(request: ResetPasswordRequest): Resource<AuthenticationDto>

}