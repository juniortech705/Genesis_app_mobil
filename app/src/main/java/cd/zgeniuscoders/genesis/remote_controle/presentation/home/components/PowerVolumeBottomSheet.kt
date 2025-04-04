package cd.zgeniuscoders.genesis.remote_controle.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cd.zgeniuscoders.genesis.common.ui.views.main.ShareEvent
import cd.zgeniuscoders.genesis.remote_controle.presentation.home.HomeEvent
import cd.zgeniuscoders.genesis.remote_controle.presentation.home.HomeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PowerVolumeBottomSheet(
    state: HomeState,
    onEvent: (event: HomeEvent) -> Unit = {},
    onShareEvent: (event: ShareEvent) -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState()

    if (state.isPowerVolumeBottomSheetOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { onEvent(HomeEvent.OnSetPower(false)) }
        ) {
            Column(
                modifier = Modifier
            ) {
                Text(
                    text = "Puissance",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    IconButton(onClick = {
                        onEvent(HomeEvent.OnPowerUp)
                        onShareEvent(ShareEvent.PowerUp("${state.powerLevel}"))
                    }) {
                        Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "keyboard up")
                    }

                    Text(text = "${state.powerLevel}")

                    IconButton(onClick = {
                        onEvent(HomeEvent.OnPowerDown)
                        onShareEvent(ShareEvent.PowerDown("${state.powerLevel}"))
                    }) {
                        Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "keyboard down")
                    }
                }
            }
        }
    }
}
