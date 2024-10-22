package cd.zgeniuscoders.genesis.authentication.domain.models.dto

import com.squareup.moshi.Json

data class AuthenticationDto(
    @Json(name = "data")
    val data: RegisterDtoData
)


data class RegisterDtoData(
    @Json(name = "token")
    val token: String? = null,

    @Json(name = "message")
    val message: String? = null,

    @Json(name = "errors")
    val errors: AuthenticationErrorDto? = null
)

fun AuthenticationDto.getToken(): String? {
    return data.token
}