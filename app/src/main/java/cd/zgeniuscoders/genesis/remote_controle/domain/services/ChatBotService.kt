package cd.zgeniuscoders.genesis.remote_controle.domain.services

import cd.zgeniuscoders.genesis.remote_controle.domain.models.ChatBot
import cd.zgeniuscoders.genesis.remote_controle.domain.models.ChatSendMessage
import kotlinx.coroutines.flow.Flow

interface ChatBotService {

    suspend fun getMessages(): Flow<List<ChatBot>>

    suspend fun sendMessage(message: ChatSendMessage)

    suspend fun startChat();

    suspend fun clearMessages()

}