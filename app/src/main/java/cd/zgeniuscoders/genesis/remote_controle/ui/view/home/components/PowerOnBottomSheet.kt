package cd.zgeniuscoders.genesis.remote_controle.ui.view.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cd.zgeniuscoders.genesis.common.ui.views.components.DefaultButton
import cd.zgeniuscoders.genesis.common.ui.views.main.ShareEvent
import cd.zgeniuscoders.genesis.remote_controle.ui.view.home.HomeEvent
import cd.zgeniuscoders.genesis.remote_controle.ui.view.home.HomeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PowerOnBottomSheet(
    state: HomeState,
    onEvent: (event: HomeEvent) -> Unit,
    onShareEvent: (event: ShareEvent) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    if (state.isPowerOnBottomSheetOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { onEvent(HomeEvent.OnPowerOnBottomSheet(false)) }
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Allumage du rechaud",
                    style = MaterialTheme.typography.titleMedium
                )

                DefaultButton(
                    text = if (state.isDevicePower) "Eteindre" else "Allumer",
                    color = if (state.isDevicePower) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onEvent(HomeEvent.OnRunDevice)
                        if (state.isDevicePower) {
                            onShareEvent(ShareEvent.PowerOff)
                        } else {
                            onShareEvent(ShareEvent.PowerOn)
                        }
                    }
                )
            }
        }
    }
}


