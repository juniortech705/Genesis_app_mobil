package cd.zgeniuscoders.genesis.common.utility

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

object GetToken {
    suspend fun getToken(context: Context): String {
        val TOKEN = stringPreferencesKey(Constants.TOKEN_KEY)
        val prefs: Flow<String> = context.preferences.data.map { prefs ->
            prefs[TOKEN] ?: ""
        }

        val token = prefs.first()

        return "Bearer $token"
    }
}