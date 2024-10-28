package cd.zgeniuscoders.genesis.authentication.domain.models.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class LogoutDto(
    @Json(name = "message")
    val message: String = ""
)