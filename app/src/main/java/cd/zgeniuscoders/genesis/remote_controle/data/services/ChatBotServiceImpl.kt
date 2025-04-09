package cd.zgeniuscoders.genesis.remote_controle.data.services

import cd.zgeniuscoders.genesis.remote_controle.data.local.ChatBotDao
import cd.zgeniuscoders.genesis.remote_controle.data.local.ChatBotEntity
import cd.zgeniuscoders.genesis.remote_controle.data.mappers.toChatBotModel
import cd.zgeniuscoders.genesis.remote_controle.domain.models.ChatBot
import cd.zgeniuscoders.genesis.remote_controle.domain.models.ChatSendMessage
import cd.zgeniuscoders.genesis.remote_controle.domain.services.ChatBotService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChatBotServiceImpl(
    private val chatBotDao: ChatBotDao
) : ChatBotService {

    override suspend fun getMessages(): Flow<List<ChatBot>> = callbackFlow {

        chatBotDao
            .all()
            .onEach { chatBotEntities ->

                val notes = chatBotEntities.map { chatEntity ->
                    chatEntity.toChatBotModel()
                }

                trySend(
                    notes
                )

            }.launchIn(CoroutineScope(Dispatchers.IO))

        awaitClose()

    }

    override suspend fun sendMessage(message: ChatSendMessage) {
        chatBotDao.add(ChatBotEntity.fromModel(message))

        val chat = when {

            message.message.contains(
                "bonjour",
                ignoreCase = true
            ) -> ChatSendMessage(message = "Salut, comment puis-je t'aider ?", isRobot = true)

            message.message.contains(
                "comment ça va",
                ignoreCase = true
            ) -> ChatSendMessage(message = "Je vais bien, merci ! Et toi ?", isRobot = true)

            message.message.contains(
                "aide",
                ignoreCase = true
            ) -> ChatSendMessage(
                message = "Je suis ici pour t'aider. Quelle est ta question ?",
                isRobot = true
            )

            else -> ChatSendMessage(message = "Désolé, je n'ai pas compris.", isRobot = true)
        }

        chatBotDao.add(ChatBotEntity.fromModel(chat))
    }

    override suspend fun startChat() {
        chatBotDao.add(
            ChatBotEntity.fromModel(
                ChatSendMessage(
                    "Salut, comment puis-je t'aider ?",
                    isRobot = true
                )
            )
        )
    }

}