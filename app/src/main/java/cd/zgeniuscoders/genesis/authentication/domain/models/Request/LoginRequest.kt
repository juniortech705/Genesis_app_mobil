package cd.zgeniuscoders.genesis.authentication.domain.models.Request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

@JsonClass(generateAdapter = true)
data class LoginRequest(
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String
)