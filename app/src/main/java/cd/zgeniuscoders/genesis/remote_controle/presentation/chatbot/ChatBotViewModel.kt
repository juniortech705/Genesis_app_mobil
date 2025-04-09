package cd.zgeniuscoders.genesis.remote_controle.presentation.chatbot

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.genesis.remote_controle.domain.models.ChatSendMessage
import cd.zgeniuscoders.genesis.remote_controle.domain.services.ChatBotService
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ChatBotViewModel(
    private val chatBotService: ChatBotService
) : ViewModel() {

    var state by mutableStateOf(ChatBotState())
        private set

    init {
        onStartChat()
        getMessage()
    }

    fun onTriggerEvent(event: ChatBotEvent) {
        when (event) {
            is ChatBotEvent.OnMessageFieldChange -> state = state.copy(message = event.message)
            ChatBotEvent.OnSendMessageButtonClick -> onSendMessage()
            ChatBotEvent.OnStartChat -> onStartChat()
        }
    }

    private fun getMessage() {
        viewModelScope.launch {
            val res = chatBotService.getMessages()
            res.onEach { m ->
                state = state.copy(messages = m)
            }.launchIn(viewModelScope)
        }
    }

    private fun onStartChat() {
        viewModelScope.launch {
            chatBotService.startChat()
        }
    }

    private fun onSendMessage() {
        viewModelScope.launch {
            chatBotService.sendMessage(ChatSendMessage(message = state.message))
        }

        state = state.copy(message = "")
    }

}