package cd.zgeniuscoders.genesis.authentication.domain.models.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class LoginDto(
    @Json(name = "message")
    val message: String = "",

    @Json(name = "data")
    val data: LoginDataDto? = null
)

data class LoginDataDto(
    @Json(name = "token")
    val token: String
)

fun LoginDto.getToken(): String {
    return data!!.token
}