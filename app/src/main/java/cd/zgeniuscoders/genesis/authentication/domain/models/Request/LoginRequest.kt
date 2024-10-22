package cd.zgeniuscoders.genesis.authentication.domain.models.Request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)