package cd.zgeniuscoders.genesis.remote_controle.ui.view.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.genesis.common.ui.views.main.ShareEvent
import cd.zgeniuscoders.genesis.common.ui.views.main.ShareState
import cd.zgeniuscoders.genesis.common.ui.views.theme.GenesisTheme
import cd.zgeniuscoders.genesis.common.utility.Routes
import cd.zgeniuscoders.genesis.remote_controle.ui.view.home.components.GenesisHomeCard
import cd.zgeniuscoders.genesis.remote_controle.ui.view.home.components.HomeCardBanner
import cd.zgeniuscoders.genesis.remote_controle.ui.view.home.components.PowerOnBottomSheet
import cd.zgeniuscoders.genesis.remote_controle.ui.view.home.components.PowerVolumeBottomSheet
import cd.zgeniuscoders.genesis.remote_controle.ui.view.home.components.TimerBottomSheet
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeView(
    navHostController: NavHostController,
    onShareEvent: (event: ShareEvent) -> Unit,
    shareState: ShareState,
    snackbarHostState: SnackbarHostState,
) {

    val vm = koinViewModel<HomeViewModel>()
    val state = vm.state
    val onEvent = vm::onTriggerEvent

    LaunchedEffect(state.timerStatus) {
        if (state.timerStatus == TimerStatus.Stop) {
            onShareEvent(ShareEvent.Buzzer)
        }
    }

    HomeBody(
        navHostController = navHostController,
        state,
        onEvent,
        onShareEvent,
        shareState,
        snackbarHostState
    )

}

@Composable
fun HomeBody(
    navHostController: NavHostController,
    state: HomeState,
    onEvent: (event: HomeEvent) -> Unit = {},
    onShareEvent: (event: ShareEvent) -> Unit = {},
    shareState: ShareState,
    snackbarHostState: SnackbarHostState,
) {

    val scope = rememberCoroutineScope()
    val homeItems = mutableListOf(
        HomeItem(
            title = "Mes appareils",
            icon = Icons.Filled.Kitchen,
            description = "Voir tous vos réchauds précédemment connectés."
        ) {
            navHostController.navigate(Routes.pairedDevice.route)
        },
        HomeItem(
            if (state.isDevicePower) "éteindre" else "allumer",
            Icons.Filled.PowerSettingsNew,
            "Cliquer ici pour allumer ou éteindre votre réchaud."
        ) {

            if (shareState.isConnected) {
                onEvent(HomeEvent.OnPowerOnBottomSheet(true))
            } else {

                scope.launch {
                    snackbarHostState.showSnackbar("Veuillez vous connecter à un réchaud avant d'utiliser ce paramètre.")
                }
            }
        },
        HomeItem(
            "Puissance",
            Icons.Filled.FlashOn,
            "Réguler la puissannce du réchaud."
        ) {
            if (shareState.isConnected) {
                onEvent(HomeEvent.OnSetPower(true))
            } else {
                scope.launch {
                    snackbarHostState.showSnackbar("Veuillez vous connecter à un réchaud avant d'utiliser ce paramètre.")
                }
            }
        },
        HomeItem(
            "Minuterie",
            Icons.Filled.Timer,
            "Suivez le temps de cuisson"
        ) {
            if (shareState.isConnected) {
                onEvent(HomeEvent.OnSetTimer(true))
            } else {
                scope.launch {
                    snackbarHostState.showSnackbar("Veuillez vous connecter à un réchaud avant d'utiliser ce paramètre.")
                }
            }
        }
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp)
    ) {


        item(
            span = { GridItemSpan(2) }
        ) {
            HomeCardBanner(state, shareState.messages, shareState.isConnected)
        }

        items(homeItems.size) {
            GenesisHomeCard(
                title = homeItems[it].title,
                icon = homeItems[it].icon,
                onClick = homeItems[it].onClick,
                description = homeItems[it].description,
            )
        }


    }

    TimerBottomSheet(
        state,
        onEvent,
        onShareEvent
    )

    PowerOnBottomSheet(
        state,
        onEvent,
        onShareEvent
    )

    PowerVolumeBottomSheet(
        state,
        onEvent,
        onShareEvent
    )
}


data class HomeItem(
    val title: String,
    val icon: ImageVector,
    val description: String,
    val onClick: () -> Unit,
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun HomePreview() {
    GenesisTheme {
        HomeBody(
            rememberNavController(),
            HomeState(),
            shareState = ShareState(),
            snackbarHostState = SnackbarHostState()
        )
    }
}