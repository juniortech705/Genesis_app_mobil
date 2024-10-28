package cd.zgeniuscoders.genesis.authentication.domain.models.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForgotPasswordDto(
    @Json(name = "errors")
    var errors: ForgotPasswordError? = null,

    @Json(name = "message")
    var message: String? = null
)

@JsonClass(generateAdapter = true)
data class ForgotPasswordError(
    @Json(name = "email")
    val email: List<String>
)
