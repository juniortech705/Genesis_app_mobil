package cd.zgeniuscoders.genesis.authentication.domain.services

import cd.zgeniuscoders.genesis.core.domain.utils.Resource
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.ForgotPasswordRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.LoginRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.OptVerificationRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.RegisterRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.ResetPasswordRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.AuthenticationDto
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.ForgotPasswordDto
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.LoginDto
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.LogoutDto
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.OptVerificationDto
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.ResetPasswordDto

interface AuthenticationService {

    suspend fun login(request: LoginRequest): Resource<LoginDto>

    suspend fun register(request: RegisterRequest): Resource<AuthenticationDto>

    suspend fun logout(): Resource<LogoutDto>

    suspend fun forgotPassword(request: ForgotPasswordRequest): Resource<ForgotPasswordDto>

    suspend fun resetPassword(request: ResetPasswordRequest): Resource<ResetPasswordDto>

    suspend fun verifyOpt(request: OptVerificationRequest): Resource<OptVerificationDto>
}