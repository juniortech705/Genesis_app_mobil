package cd.zgeniuscoders.genesis.remote_controle.presentation.pairedDevice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BluetoothSearching
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.genesis.common.ui.views.components.TopBarWithBackButton
import cd.zgeniuscoders.genesis.common.ui.views.main.ShareEvent
import cd.zgeniuscoders.genesis.common.ui.views.main.ShareState
import cd.zgeniuscoders.genesis.core.presentation.components.DeviceLists
import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothDevice

@Composable
fun PairedDeviceView(
    navHostController: NavHostController,
    pairedDevices: List<BluetoothDevice>,
    onShareEvent: (event: ShareEvent) -> Unit,
    state: ShareState,
    snackbarHostState: SnackbarHostState
) {
    AddDeviceBody(navHostController, pairedDevices, onShareEvent, state)
}

@Composable
fun AddDeviceBody(
    navHostController: NavHostController,
    pairedDevices: List<BluetoothDevice>,
    onShareEvent: (event: ShareEvent) -> Unit = {},
    state: ShareState = ShareState(),
) {

    Scaffold(
        topBar = {
            TopBarWithBackButton(
                navHostController = navHostController,
                title = "Réchauds Connus",
                isActionButtonShow = false,
                {}, {}
            )
        }
    ) { innerP ->
        Column(
            modifier = Modifier
                .padding(innerP)
                .fillMaxSize()
        ) {

            if (pairedDevices.isEmpty()) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    Icon(
                        Icons.Filled.BluetoothSearching,
                        modifier = Modifier.size(60.dp),
                        contentDescription = "bluetooth icon search"
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Aucun périphérique Bluetooth n'est connecté à ce téléphone.",
                        textAlign = TextAlign.Center
                    )
                }
            } else {

                DeviceLists(
                    isConnecting = state.isConnecting,
                    isConnected = state.isConnected,
                    devices = state.pairedDevices
                ) {
                    onShareEvent(it)
                }

            }
        }
    }

}


@Composable
@Preview(showBackground = true)
fun AddDevicePreview() {
    AddDeviceBody(rememberNavController(), emptyList())
}