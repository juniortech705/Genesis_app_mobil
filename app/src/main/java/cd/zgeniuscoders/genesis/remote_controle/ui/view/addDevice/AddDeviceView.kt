package cd.zgeniuscoders.genesis.remote_controle.ui.view.addDevice


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cd.zgeniuscoders.genesis.common.ui.views.main.ShareState
import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothDevice
import cd.zgeniuscoders.genesis.remote_controle.ui.view.addDevice.components.AnimateSearch
import cd.zgeniuscoders.genesis.remote_controle.ui.view.addDevice.components.InfinitelyFlowingCircles


@Composable
fun AddDeviceView(
    navHostController: NavHostController,
    state: ShareState,
    startScan: () -> Unit,
    onDeviceClick: (BluetoothDevice) -> Unit
) {

    LaunchedEffect(true) {
        startScan()
    }

    AddDeviceBody(
        state,
        onDeviceClick,
        state.isScanning
    )

}

@Composable
fun AddDeviceBody(
    state: ShareState,
    onDeviceClick: (BluetoothDevice) -> Unit = {},
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

            when {
                state.isConnecting -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }

                state.isConnected -> {
//                    navHostController.navigate(Routes.controlDevice.route)
                }

                else -> {
                    LazyColumn {
                        items(state.scannedDevices) {
                            val device = it
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        onDeviceClick(device)
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(Icons.Filled.Kitchen, contentDescription = null)
                                Column {
                                    Text(device.name)
                                    Text(
                                        device.address,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }
    }

}


@Composable
@Preview(showBackground = true)
fun AddDevicePreview() {
    AddDeviceBody(ShareState())
}