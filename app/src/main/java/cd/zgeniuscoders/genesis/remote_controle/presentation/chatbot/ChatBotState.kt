package cd.zgeniuscoders.genesis.remote_controle.presentation.chatbot

import cd.zgeniuscoders.genesis.remote_controle.domain.models.ChatBot

data class ChatBotState(
    val isLoading: Boolean = false,
    val message: String = "",
    val messages: List<ChatBot> = emptyList()
)
