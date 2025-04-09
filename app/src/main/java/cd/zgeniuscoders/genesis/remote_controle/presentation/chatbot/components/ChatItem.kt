package cd.zgeniuscoders.confidences.chat.presentation.chat.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import cd.zgeniuscoders.genesis.remote_controle.domain.models.ChatBot
import cd.zgeniuscoders.genesis.remote_controle.presentation.chatbot.ChatBotEvent

@Composable
fun ChatItem(message: ChatBot, onEvent: (event: ChatBotEvent) -> Unit) {

    var showDeleteForMeDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 2.dp, horizontal = 10.dp),
        horizontalAlignment = if (message.isRobot) Alignment.End else Alignment.Start
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .pointerInput(true) {
                    detectTapGestures(
                        onLongPress = {
                            showDeleteForMeDialog = true
                        },
                    )
                }, colors = CardDefaults.cardColors(
                containerColor = if (message.isRobot) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surfaceContainerHigh
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    modifier = Modifier.weight(0.8f)
                ) {
                    Text(message.message)
                }

            }
        }

    }

}
