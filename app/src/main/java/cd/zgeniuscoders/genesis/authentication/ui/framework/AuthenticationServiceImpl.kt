package cd.zgeniuscoders.genesis.authentication.ui.framework

import android.content.Context
import android.util.Log
import cd.zgeniuscoders.genesis.common.utility.GetToken
import cd.zgeniuscoders.genesis.common.utility.Resource
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
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.RegisterDtoData
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.ResetPasswordDto
import cd.zgeniuscoders.genesis.authentication.domain.services.AuthenticationApi
import cd.zgeniuscoders.genesis.authentication.domain.services.AuthenticationService
import cd.zgeniuscoders.genesis.core.utility.getHttpError
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.HttpException
import java.io.EOFException
import java.io.IOException

class AuthenticationServiceImpl(private val api: AuthenticationApi, private val context: Context) :
    AuthenticationService {


    override suspend fun login(request: LoginRequest): Resource<LoginDto> {

        return try {
            val contentType = "application/json"

            val data = api.login(
                contentType,
                request
            )

            Resource.Success(
                data = data
            )

        } catch (e: HttpException) {

            e.printStackTrace()

            Resource.Error(
                message = "Adresse e-mail ou mot de passe incorect"
            )

        } catch (e: IOException) {
            e.printStackTrace()

            Resource.Error(
                message = "Connexion failed"
            )

        }

    }

    override suspend fun register(request: RegisterRequest): Resource<AuthenticationDto> {

        return try {

            val contentType = "application/json"

            val data = api.register(
                contentType,
                request
            )

            Resource.Success(
                data = data
            )

        } catch (e: HttpException) {

            val errorJson = e.response()?.errorBody()?.string()
//            val authError = getAuthErrorHttp(errorJson!!)

            e.printStackTrace()

            Log.e("register", errorJson.toString())

            Resource.Error(
                message = "Validation failed"
            )

        } catch (e: IOException) {

            e.printStackTrace()

            Resource.Error(
                message = "Une erreur inattendue s'est produite. Veuillez réessayer plus tard."
            )
        }


    }

    override suspend fun logout(): Resource<LogoutDto> {
        return try {
            val res = api.logout(
                "application/json",
                GetToken.getToken(context)
            )

            Resource.Success(
                data = LogoutDto(message = "success")
            )

        } catch (e: IOException) {
            e.printStackTrace()

            Resource.Error(
                message = "Connection error"
            )
        }
    }

    override suspend fun forgotPassword(request: ForgotPasswordRequest): Resource<ForgotPasswordDto> {
        return try {
            val res = api.forgot("application/json", request)
            Resource.Success(data = res)
        } catch (e: IOException) {
            Resource.Error("An excepted error occurred")
        } catch (e: HttpException) {
            val errorJson = e.response()?.errorBody()?.string()

            val error = getHttpError(errorJson!!, ForgotPasswordDto::class.java)

            Resource.Error(data = error, message = "failed")
        }catch (e: EOFException){
            e.printStackTrace()
            Resource.Error(message = "An excepted error occurred")
        }
    }

    override suspend fun resetPassword(request: ResetPasswordRequest): Resource<ResetPasswordDto> {
        return try {
            val res = api.reset("application/json", request)
            Resource.Success(data = res)
        } catch (e: IOException) {
            Resource.Error("An excepted error occurred")
        } catch (e: HttpException) {
            val errorJson = e.response()?.errorBody()?.string()

            val error = getHttpError(errorJson!!, ResetPasswordDto::class.java)

            Resource.Error(data = error, message = "failed")
        }
    }

    override suspend fun verifyOpt(request: OptVerificationRequest): Resource<OptVerificationDto> {
        TODO("Not yet implemented")
    }


    private fun getAuthErrorHttp(errorJson: String): RegisterDtoData {

        val adapter: JsonAdapter<RegisterDtoData> =
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
                .adapter(RegisterDtoData::class.java)
                .lenient()

        return if (!errorJson.contains("{"))
            RegisterDtoData(message = errorJson)
        else
            adapter.fromJson(errorJson)!!
    }


    private fun getLoginErrorHttp(errorJson: String): LoginDto {

        val adapter: JsonAdapter<LoginDto> =
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
                .adapter(LoginDto::class.java)
                .lenient()

        return if (!errorJson.contains("{"))
            LoginDto(message = errorJson)
        else
            adapter.fromJson(errorJson)!!
    }


}