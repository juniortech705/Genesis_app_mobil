package cd.zgeniuscoders.speedshop.authentication.domain.mappers

import cd.zgeniuscoders.genesis.authentication.domain.models.dto.AuthenticationDto

fun AuthenticationDto.toAuthenticationErrorHashMap(): HashMap<String, List<String>> {
    return hashMapOf(
        "username" to (data.errors?.username ?: emptyList()),
        "password" to (data.errors?.password ?: emptyList()),
        "email" to (data.errors?.email ?: emptyList())
    )
}

