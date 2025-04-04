@file:OptIn(ExperimentalMaterial3Api::class)

package cd.zgeniuscoders.genesis.core.presentation.main

import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BluetoothConnected
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.genesis.common.ui.views.components.TopBarWithBackButton
import cd.zgeniuscoders.genesis.common.ui.views.components.drawer.DrawerBody
import cd.zgeniuscoders.genesis.common.ui.views.splash_screen.SplashView
import cd.zgeniuscoders.genesis.core.domain.utils.Routes
import cd.zgeniuscoders.genesis.authentication.ui.presentation.forgot.ForgotPasswordView
import cd.zgeniuscoders.genesis.authentication.ui.presentation.login.LoginView
import cd.zgeniuscoders.genesis.authentication.ui.presentation.opt_verification.OptVerificationView
import cd.zgeniuscoders.genesis.authentication.ui.presentation.profile.ProfileView
import cd.zgeniuscoders.genesis.authentication.ui.presentation.register.RegisterView
import cd.zgeniuscoders.genesis.authentication.ui.presentation.reset.ResetPasswordView
import cd.zgeniuscoders.genesis.common.ui.views.main.ShareEvent
import cd.zgeniuscoders.genesis.remote_controle.presentation.addDevice.AddDeviceView
import cd.zgeniuscoders.genesis.remote_controle.presentation.home.HomeView
import cd.zgeniuscoders.genesis.remote_controle.presentation.pairedDevice.PairedDeviceView
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@ExperimentalMaterial3Api
@Composable
fun RoutePage(
    navHostController: NavHostController
) {

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val route = currentDestination?.route

    var topBarTitle by remember {
        mutableStateOf("Découverte de Dispositifs")
    }
    var isSpeaking by remember { mutableStateOf(false) }
    val tts = rememberTextToSpeech()

    var isTopBarShow by remember {
        mutableStateOf(false)
    }
    var isAddDeviceTopBarShow by remember {
        mutableStateOf(false)
    }

    val topBarPage = listOf(
        Routes.home.route
    )

    val customTopBarPage = listOf(
        Routes.profile.route,
        Routes.connectDevice.route,
        Routes.pairedDevice.route,
        Routes.controlDevice.route
    )

    topBarTitle = when (route) {
        Routes.profile.route -> {
            "Profile"
        }

        Routes.pairedDevice.route -> {
            "Réchauds Connus"
        }

        Routes.controlDevice.route -> {
            "Contrôle du réchaud BT_GENESIS"
        }

        else -> {
            "Découverte"
        }
    }

    isAddDeviceTopBarShow = route in customTopBarPage
    isTopBarShow = route in topBarPage

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

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarHostState = SnackbarHostState()
    val onEvent = shareViewModel::onTriggerEvent

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet(
            ) {
                DrawerBody(
                    navHostController = navHostController,
                    drawerState = drawerState,
                    onEvent
                )
            }
        }) {
        Scaffold(
            topBar = {
                if (route in topBarPage) {
                    CenterAlignedTopAppBar(
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(imageVector = Icons.Default.Sort, contentDescription = null)
                            }
                        },
                        title = { Text("Genesis") }
                    )
                }
                if (isAddDeviceTopBarShow) {
                    TopBarWithBackButton(
                        navHostController = navHostController,
                        title = topBarTitle,
                        isActionButtonShow = route === Routes.connectDevice.route,
                        shareViewModel::startScan,
                        shareViewModel::stopScan
                    )
                }
            },
            floatingActionButton = {
                if (isTopBarShow) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

//                        if(state.isConnected){
                        FloatingActionButton(
                            containerColor = MaterialTheme.colorScheme.primary,
                            onClick = {
                                onEvent(ShareEvent.startSpeech)
                            }) {
                            Icon(
                                Icons.Filled.Mic,
                                contentDescription = "record audio",
                                tint = Color.White
                            )
//                            }
                        }

                        FloatingActionButton(
                            containerColor = MaterialTheme.colorScheme.primary,
                            onClick = {
                                navHostController.navigate(Routes.connectDevice.route)
                            }) {
                            Icon(
                                Icons.Filled.BluetoothConnected,
                                contentDescription = "pair new device",
                                tint = Color.White
                            )
                        }


                    }
                }
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { innerP ->
            NavHost(
                navController = navHostController,
                startDestination = Routes.splash_screen.route,
                modifier = Modifier
                    .padding(innerP)
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
                        state = state,

                        )
                }

                composable(route = Routes.pairedDevice.route) {
                    PairedDeviceView(
                        navHostController,
                        state.pairedDevices,
                        shareViewModel::onTriggerEvent,
                        state
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