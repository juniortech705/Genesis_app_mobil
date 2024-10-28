package cd.zgeniuscoders.genesis.authentication.domain.models.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResetPasswordDto(
    @Json(name = "errors")
    val errors: ResetPasswordError? = null,

    @Json(name = "message")
    val message: String? = null
)

@JsonClass(generateAdapter = true)
data class ResetPasswordError(
    @Json(name = "otp_token")
    val otpToken: String,

    @Json(name = "new_password")
    val password: List<String>,

    @Json(name = "c_new_password")
    val confirmPassword: List<String>
)
