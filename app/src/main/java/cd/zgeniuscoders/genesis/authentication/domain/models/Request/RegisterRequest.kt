package cd.zgeniuscoders.genesis.authentication.domain.models.Request


import cd.zgeniuscoders.genesis.authentication.domain.models.User
import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    @Json(name = "name")
    val username: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "c_password")
    val confirmPassword: String,
    @Json(name = "email")
    val email: String,
){

    fun toModel(): User {
        return User("0",username,email)
    }

}