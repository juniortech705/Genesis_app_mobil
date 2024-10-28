package cd.zgeniuscoders.genesis.core.utility

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

fun <T> getHttpError(errorJson: String, dto: Class<out T & Any>): T? {
    val adapter: JsonAdapter<out T>? =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter(dto)
            .lenient()

    return if (!errorJson.contains("{"))
        null
    else
        adapter?.fromJson(errorJson)!!
}