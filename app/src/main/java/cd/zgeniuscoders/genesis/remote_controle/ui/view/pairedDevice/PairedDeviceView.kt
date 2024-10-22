package cd.zgeniuscoders.genesis.remote_controle.ui.view.pairedDevice

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BluetoothSearching
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.genesis.common.ui.views.main.ShareState
import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothDevice
import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothDeviceModel


@Composable
fun PairedDeviceView(
    navHostController: NavHostController,
    pairedDevices: List<BluetoothDevice>,
    onDeviceClick: (BluetoothDeviceModel) -> Unit,
    state: ShareState
) {
    AddDeviceBody(navHostController, pairedDevices, onDeviceClick, state)
}

@Composable
fun AddDeviceBody(
    navHostController: NavHostController,
    pairedDevices: List<BluetoothDevice>,
    onDeviceClick: (BluetoothDeviceModel) -> Unit = {},
    state: ShareState = ShareState(),

    ) {
    Column(
        modifier = Modifier.fillMaxSize()
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
                        items(pairedDevices.size) {
                            val device = pairedDevices[it]
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = 10.dp)
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
    AddDeviceBody(rememberNavController(), emptyList())
}