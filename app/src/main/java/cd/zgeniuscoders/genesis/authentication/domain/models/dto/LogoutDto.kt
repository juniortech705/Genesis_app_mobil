package cd.zgeniuscoders.genesis.authentication.domain.models.dto

import com.squareup.moshi.Json


class LogoutDto(
    @Json(name = "message")
    val message: String = ""
)