package cd.zgeniuscoders.genesis.authentication.domain.models.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthenticationErrorDto(
    @Json(name = "message")
    val message: String = "",
    @Json(name = "name")
    val username: List<String> = emptyList(),
    @Json(name = "email")
    val email: List<String> = emptyList(),
    @Json(name = "password")
    val password: List<String> = emptyList(),
)

