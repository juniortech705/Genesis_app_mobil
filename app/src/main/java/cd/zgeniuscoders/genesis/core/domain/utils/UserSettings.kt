package cd.zgeniuscoders.genesis.core.domain.utils

import kotlinx.serialization.Serializable


@Serializable
data class UserSettings(
    val isLogged: Boolean = false,
    val token: String? = null
)