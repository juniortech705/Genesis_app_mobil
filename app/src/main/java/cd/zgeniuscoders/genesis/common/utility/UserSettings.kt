package cd.zgeniuscoders.genesis.common.utility

import kotlinx.serialization.Serializable


@Serializable
data class UserSettings(
    val isLogged: Boolean = false,
    val token: String? = null
)