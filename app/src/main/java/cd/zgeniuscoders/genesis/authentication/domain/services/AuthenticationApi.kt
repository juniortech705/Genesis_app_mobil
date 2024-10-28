package cd.zgeniuscoders.genesis.authentication.domain.services

import cd.zgeniuscoders.genesis.authentication.domain.models.Request.ForgotPasswordRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.LoginRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.OptVerificationRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.RegisterRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.Request.ResetPasswordRequest
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.AuthenticationDto
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.ForgotPasswordDto
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.LoginDto
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.OptVerificationDto
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.ResetPasswordDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST


interface AuthenticationApi {

    @POST("/api/login")
    suspend fun login(
        @Header("Content-Type") contentType: String,
        @Body requestBody: LoginRequest
    ): LoginDto

    @POST("/api/register")
    suspend fun register(
        @Header("Content-Type") contentType: String,
        @Body requestBody: RegisterRequest
    ): AuthenticationDto

    @POST("/api/password/reset")
    suspend fun reset(
        @Header("Content-Type") contentType: String,
        @Body requestBody: ResetPasswordRequest
    ): ResetPasswordDto

    @POST("/api/password/forgot")
    suspend fun forgot(
        @Header("Content-Type") contentType: String,
        @Body requestBody: ForgotPasswordRequest
    ): ForgotPasswordDto

    @POST("/api/password/verify-otp")
    suspend fun optVerify(
        @Header("Content-Type") contentType: String,
        @Body requestBody: OptVerificationRequest
    ): OptVerificationDto

    @POST("/api/logout")
    suspend fun logout(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
    )

}