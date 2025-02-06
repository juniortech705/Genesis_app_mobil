package cd.zgeniuscoders.genesis.remote_controle.presentation.home.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cd.zgeniuscoders.genesis.remote_controle.domain.models.Device

@Composable
fun DeviceListItem(device: Device) {
    Row {
        Text(device.name)
        Text(device.address)
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showBackground = true)
fun DeviceItemPreview(){
    Scaffold {
        DeviceListItem(Device("Device 1", "1234567890"))
    }
}