package cd.zgeniuscoders.genesis.common.ui.views.splash_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.genesis.R
import cd.zgeniuscoders.genesis.core.domain.utils.preferences
import cd.zgeniuscoders.genesis.core.domain.utils.Constants
import cd.zgeniuscoders.genesis.core.domain.utils.Routes
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SplashView(navHostController: NavHostController) {

    val context = LocalContext.current

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_special),
                contentDescription = "genesis logo",
                modifier = Modifier.width(200.dp)
            )
        }


        val IS_LOGGED = booleanPreferencesKey(Constants.IS_LOGGED_KEY)
        val prefs: Flow<Boolean> = context.preferences.data.map { prefs ->
            prefs[IS_LOGGED] ?: false
        }

        val isLogged = prefs.collectAsState(initial = false)

        LaunchedEffect(true) {
            delay(2000)

            if (isLogged.value) {
                navHostController.navigate(Routes.home.route)
            } else {
                navHostController.navigate(Routes.login.route)
            }

        }
    }
}

@Composable
@Preview(showBackground = true)
fun SplashPreview() {
    SplashView(navHostController = rememberNavController())
}