package cd.zgeniuscoders.genesis.remote_controle.presentation.chatbot

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cd.zgeniuscoders.confidences.chat.presentation.chat.components.ChatItem
import cd.zgeniuscoders.genesis.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatBotPage(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    val vm = koinViewModel<ChatBotViewModel>()
    val state = vm.state
    val onEvent = vm::onTriggerEvent

    ChatBotBody(
        state,
        onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBotBody(
    state: ChatBotState,
    onEvent: (event: ChatBotEvent) -> Unit
) {
    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
            ) {
                TextField(
                    modifier = Modifier
                        .weight(0.8f)
                        .heightIn(min = 50.dp, max = 120.dp),
                    trailingIcon = {
                        IconButton(
                            onClick = { onEvent(ChatBotEvent.OnSendMessageButtonClick) },
                            enabled = state.message.isNotEmpty()
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Send,
                                contentDescription = "send icon"
                            )
                        }
                    },
                    value = state.message,
                    onValueChange = {
                        onEvent(ChatBotEvent.OnMessageFieldChange(it))
                    },
                    maxLines = 4,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Image(
                            modifier = Modifier.size(40.dp),
                            painter = painterResource(id = R.drawable.logo_special),
                            contentDescription = "logo special"
                        )
                        Text("Athena")
                    }
                }
            )
        })
    { innerP ->
        LazyColumn(
            modifier = Modifier
                .padding(innerP)
                .fillMaxSize()
        ) {
            items(state.messages) { message ->
                ChatItem(
                    message = message,
                    onEvent
                )
            }
        }

    }
}