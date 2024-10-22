package cd.zgeniuscoders.genesis.authentication.ui.framework

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import cd.zgeniuscoders.genesis.common.utility.preferences
import cd.zgeniuscoders.genesis.common.utility.Constants
import cd.zgeniuscoders.genesis.common.utility.UserSettings

class SessionService(
    val context: Context
) {

    private val TOKEN = stringPreferencesKey(Constants.TOKEN_KEY)
    private val IS_LOGGED = booleanPreferencesKey(Constants.IS_LOGGED_KEY)

    suspend fun addUserToSession(userSettings: UserSettings) {
        context.preferences.edit { settings ->
            settings[TOKEN] = userSettings.token!!
            settings[IS_LOGGED] = userSettings.isLogged
        }
    }

    suspend fun clearUserPref(){
        context.preferences.edit { settings ->
            settings[TOKEN] = ""
            settings[IS_LOGGED] = false
        }
    }

}