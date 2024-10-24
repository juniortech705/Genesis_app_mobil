package cd.zgeniuscoders.genesis.core.ui.views.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BluetoothConnected
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cd.zgeniuscoders.genesis.common.ui.views.main.ShareEvent
import cd.zgeniuscoders.genesis.common.ui.views.theme.GenesisTheme
import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothDeviceModel

@Composable
fun DeviceLists(
    isConnecting: Boolean,
    isConnected: Boolean,
    devices: List<BluetoothDeviceModel>,
    onShareEvent: (event: ShareEvent) -> Unit
) {

    when {
        isConnecting -> {
            AlertDialog(
                modifier = Modifier.height(200.dp),
                icon = {
                    Icon(
                        Icons.Filled.BluetoothConnected,
                        contentDescription = "Example Icon"
                    )
                },
                onDismissRequest = { /*TODO*/ },
                confirmButton = { /*TODO*/ },
                title = {
                    Text(text = "Connexion...")
                },
                text = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                })
        }

        isConnected -> {
//                    navHostController.navigate(Routes.controlDevice.route)
        }

        else -> {
        }
    }

    LazyColumn {
        items(devices) {
            val device = it
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        onShareEvent(ShareEvent.ConnectDevice(device))
                    }
                    .padding(10.dp),
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
            Divider()
        }
    }

}

@Preview
@Composable
fun DeviceListPreview(modifier: Modifier = Modifier) {
    GenesisTheme {
        Scaffold { innerP ->
            DeviceLists(
                isConnecting = false, isConnected = false, devices = listOf(
                    BluetoothDeviceModel("Genis-BT", "bb:df:20d:4"),
                    BluetoothDeviceModel("Genis-BT", "bb:df:20d:4"),
                    BluetoothDeviceModel("Genis-BT", "bb:df:20d:4"),
                )
            ) {

            }
        }
    }
}