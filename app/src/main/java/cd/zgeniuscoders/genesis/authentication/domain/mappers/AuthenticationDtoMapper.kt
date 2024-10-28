package cd.zgeniuscoders.speedshop.authentication.domain.mappers

import cd.zgeniuscoders.genesis.authentication.domain.models.dto.AuthenticationDto
import cd.zgeniuscoders.genesis.authentication.domain.models.dto.ForgotPasswordDto

fun AuthenticationDto.toAuthenticationErrorHashMap(): HashMap<String, List<String>> {
    return hashMapOf(
        "username" to (data.errors?.username ?: emptyList()),
        "password" to (data.errors?.password ?: emptyList()),
        "email" to (data.errors?.email ?: emptyList())
    )
}

fun ForgotPasswordDto.getEmailError(): String {
    return errors?.let{
        it.email[0]
    } ?: ""
}