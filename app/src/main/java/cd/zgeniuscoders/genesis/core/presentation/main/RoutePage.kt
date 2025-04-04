@file:OptIn(ExperimentalMaterial3Api::class)

package cd.zgeniuscoders.genesis.core.presentation.main

import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.genesis.common.ui.views.splash_screen.SplashView
import cd.zgeniuscoders.genesis.core.domain.utils.Routes
import cd.zgeniuscoders.genesis.authentication.ui.presentation.forgot.ForgotPasswordView
import cd.zgeniuscoders.genesis.authentication.ui.presentation.login.LoginView
import cd.zgeniuscoders.genesis.authentication.ui.presentation.opt_verification.OptVerificationView
import cd.zgeniuscoders.genesis.authentication.ui.presentation.profile.ProfileView
import cd.zgeniuscoders.genesis.authentication.ui.presentation.register.RegisterView
import cd.zgeniuscoders.genesis.authentication.ui.presentation.reset.ResetPasswordView
import cd.zgeniuscoders.genesis.remote_controle.presentation.addDevice.AddDeviceView
import cd.zgeniuscoders.genesis.remote_controle.presentation.home.HomeView
import cd.zgeniuscoders.genesis.remote_controle.presentation.pairedDevice.PairedDeviceView
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@ExperimentalMaterial3Api
@Composable
fun RoutePage(
    navHostController: NavHostController
) {

    val tts = rememberTextToSpeech()

    val shareViewModel = koinViewModel<ShareViewModel>()
    val state by shareViewModel.state.collectAsState()

    val appContext = LocalContext.current

    LaunchedEffect(state.speechMessage) {
        if (state.speechMessage.isNotBlank()) {
            tts.value?.speak(
                state.speechMessage, TextToSpeech.QUEUE_FLUSH, null, ""
            )
        }
    }
    LaunchedEffect(key1 = state.isConnected) {
        if (state.isConnected) {
            Toast.makeText(
                appContext,
                "Vous êtes connecté !",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    LaunchedEffect(key1 = state.errorMessage) {
        state.errorMessage?.let { message ->
            Toast.makeText(
                appContext,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    val snackbarHostState = SnackbarHostState()
    val onEvent = shareViewModel::onTriggerEvent

    NavHost(
        navController = navHostController,
        startDestination = Routes.splash_screen.route,
        modifier = Modifier
            .fillMaxSize()
    ) {

        composable(route = Routes.splash_screen.route) {
            SplashView(navHostController)
        }
        composable(route = Routes.home.route) {
            HomeView(
                navHostController,
                shareViewModel::onTriggerEvent,
                state,
                snackbarHostState
            )
        }
        composable(route = Routes.profile.route) {
            ProfileView(navHostController)
        }
        composable(route = Routes.connectDevice.route) {
            AddDeviceView(
                navHostController,
                onShareEvent = shareViewModel::onTriggerEvent,
                startScan = shareViewModel::startScan,
                stopScan = shareViewModel::stopScan,
                state = state,
            )
        }
        composable(route = Routes.pairedDevice.route) {
            PairedDeviceView(
                navHostController,
                state.pairedDevices,
                onEvent,
                state,
                snackbarHostState
            )
        }
        composable(route = Routes.login.route) {
            LoginView(navHostController)
        }
        composable(route = Routes.register.route) {
            RegisterView(navHostController)
        }
        composable(route = Routes.opt_verification.route) {
            OptVerificationView(navHostController)
        }
        composable(route = Routes.forgot_password.route) {
            ForgotPasswordView(navHostController)
        }
        composable(route = Routes.reset_password.route) {
            ResetPasswordView(navHostController)
        }

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun RoutePagePreview() {
    RoutePage(rememberNavController())
}

@Composable
fun rememberTextToSpeech(): MutableState<TextToSpeech?> {
    val context = LocalContext.current
    val tts = remember { mutableStateOf<TextToSpeech?>(null) }
    DisposableEffect(context) {
        val textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.value?.language = Locale.FRENCH
            }
        }
        tts.value = textToSpeech

        onDispose {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
    }
    return tts
}