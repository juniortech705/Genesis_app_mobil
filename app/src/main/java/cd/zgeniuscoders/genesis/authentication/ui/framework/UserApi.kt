package cd.zgeniuscoders.genesis.authentication.ui.framework

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApi {

    @POST("/api/login/")
    suspend fun login(
        @Header("Content-Type") contentType: String,
        @Body requestBody: RequestBody
    )

    @POST("/api/register/")
    suspend fun register(
        @Header("Content-Type") contentType: String,
        @Body requestBody: RequestBody
    )

}