package cd.zgeniuscoders.genesis.authentication.domain.models.Request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OptVerificationRequest(
    @Json(name = "otp_code")
    val optCode: String
)
