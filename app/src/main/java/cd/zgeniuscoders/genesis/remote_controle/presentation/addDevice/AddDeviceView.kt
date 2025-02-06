package cd.zgeniuscoders.genesis.remote_controle.presentation.addDevice


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cd.zgeniuscoders.genesis.common.ui.views.main.ShareEvent
import cd.zgeniuscoders.genesis.common.ui.views.main.ShareState
import cd.zgeniuscoders.genesis.core.presentation.components.DeviceLists
import cd.zgeniuscoders.genesis.remote_controle.presentation.addDevice.components.AnimateSearch
import cd.zgeniuscoders.genesis.remote_controle.presentation.addDevice.components.InfinitelyFlowingCircles


@Composable
fun AddDeviceView(
    navHostController: NavHostController,
    state: ShareState,
    startScan: () -> Unit,
    onShareEvent: (event: ShareEvent) -> Unit
) {

    LaunchedEffect(true) {
        startScan()
    }

    AddDeviceBody(
        state,
        onShareEvent,
        state.isScanning
    )

}

@Composable
fun AddDeviceBody(
    state: ShareState,
    onShareEvent: (event: ShareEvent) -> Unit = {},
    scanning: Boolean = true
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.pairedDevices.isEmpty()) {
            if (scanning) {
                Box {
                    InfinitelyFlowingCircles()

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 28.dp, end = 28.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        AnimateSearch()
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Aucun réchaud n'a été trouvé.")
                }
            }

        } else {

            DeviceLists(
                isConnecting = state.isConnecting,
                isConnected = state.isConnected,
                devices = state.scannedDevices
            ) {
                onShareEvent(it)
            }
        }
    }

}


@Composable
@Preview(showBackground = true)
fun AddDevicePreview() {
    AddDeviceBody(ShareState())
}