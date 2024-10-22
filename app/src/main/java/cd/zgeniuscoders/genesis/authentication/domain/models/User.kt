package cd.zgeniuscoders.genesis.authentication.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String,
    val email: String,
    val profile: String? = null,
    val email_verified_at: String? = null,
)
