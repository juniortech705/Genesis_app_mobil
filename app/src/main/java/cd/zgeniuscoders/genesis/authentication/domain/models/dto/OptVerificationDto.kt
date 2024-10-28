package cd.zgeniuscoders.genesis.authentication.domain.models.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OptVerificationDto(
    @Json(name = "errors")
    val errors: OptVerificationError? = null,

    @Json(name = "message")
    val message: String? = null,

    @Json(name = "otp_token")
    val otpToken: String? = null,

    @Json(name = "error")
    val error: String? = null

)

@JsonClass(generateAdapter = true)
data class OptVerificationError(
    @Json(name = "otp_code")
    val otpCode: List<String>
)
