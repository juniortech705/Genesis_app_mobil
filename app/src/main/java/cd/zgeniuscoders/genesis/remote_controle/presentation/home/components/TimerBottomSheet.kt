package cd.zgeniuscoders.genesis.remote_controle.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cd.zgeniuscoders.genesis.common.ui.views.components.DefaultButton
import cd.zgeniuscoders.genesis.remote_controle.presentation.home.HomeEvent
import cd.zgeniuscoders.genesis.remote_controle.presentation.home.HomeState
import cd.zgeniuscoders.genesis.authentication.ui.presentation.components.TextFieldComponent
import cd.zgeniuscoders.genesis.common.ui.views.main.ShareEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerBottomSheet(
    state: HomeState,
    onEvent: (event: HomeEvent) -> Unit,
    onShareEvent: (event: ShareEvent) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    if (state.isTimerBottomSheetOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { onEvent(HomeEvent.OnSetTimer(false)) }
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Réglages minuteur",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(20.dp))

                if (state.isPlaying) {
                    DefaultButton(
                        text = "Arreter",
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onEvent(HomeEvent.OnStopTimer)
                        }
                    )
                } else {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TextFieldComponent(
                            modifier = Modifier.weight(1f),
                            textValue = state.minutes,
                            label = "Minute",
                            keyboardType = KeyboardType.Number,
                            content = { },
                            onValueChange = { onEvent(HomeEvent.UpdateHour(it)) },
                            isPasswordField = false
                        )

                        TextFieldComponent(
                            modifier = Modifier.weight(1f),
                            textValue = state.secondes,
                            label = "Secondes",
                            keyboardType = KeyboardType.Number,
                            content = { },
                            onValueChange = { onEvent(HomeEvent.UpdateMinute(it)) },
                            isPasswordField = false
                        )
                    }

                    DefaultButton(
                        text = "Enregistrer",
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onEvent(HomeEvent.OnLaunchTimer)
//                            onMessage("TIMER ${state.minutes}:${state.secondes}")
                        }
                    )
                }

            }

        }
    }
}
