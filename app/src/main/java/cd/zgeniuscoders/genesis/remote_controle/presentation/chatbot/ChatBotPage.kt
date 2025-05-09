package cd.zgeniuscoders.genesis.remote_controle.presentation.chatbot

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import cd.zgeniuscoders.confidences.chat.presentation.chat.components.ChatItem
import cd.zgeniuscoders.genesis.R
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@Composable
fun ChatBotPage(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    val vm = koinViewModel<ChatBotViewModel>()
    val state = vm.state
    val onEvent = vm::onTriggerEvent

    ChatBotBody(
        state = state,
        onEvent = onEvent,
        context = LocalContext.current
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBotBody(
    state: ChatBotState,
    onEvent: (event: ChatBotEvent) -> Unit,
    context: Context
) {
    rememberCoroutineScope()
    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }
    val recognizerIntent = remember {
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        }
    }
    val tts = remember {
        TextToSpeech(context) { status ->
            if (status != TextToSpeech.SUCCESS) {
                Log.e("TTS", "Initialisation échouée")
            }
        }
    }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color(0xFFEFEFEF))
            ) {
                TextField(
                    modifier = Modifier
                        .weight(0.8f)
                        .heightIn(min = 50.dp, max = 120.dp),
                    trailingIcon = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = {
                                    onEvent(ChatBotEvent.OnSendVoiceMessage)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Mic,
                                    contentDescription = "Micro du chatbot",
                                    tint = Color(0xFF2196F3)
                                )
                            }

                            IconButton(
                                onClick = { onEvent(ChatBotEvent.OnSendMessageButtonClick) },
                                enabled = state.message.isNotEmpty()
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Send,
                                    contentDescription = "send icon",
                                    tint = Color(0xFF2196F3)
                                )
                            }

                            IconButton(
                                onClick = {
                                    onEvent(ChatBotEvent.ClearMessages)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Effacer la conversation",
                                    tint = Color.Red
                                )
                            }
                        }
                    },
                    value = state.message,
                    onValueChange = {
                        onEvent(ChatBotEvent.OnMessageFieldChange(it))
                    },
                    maxLines = 4,
                    textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
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
                    onEvent = onEvent
                )
            }
        }
    }
}
