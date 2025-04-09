package cd.zgeniuscoders.genesis.remote_controle.data.mappers

import cd.zgeniuscoders.genesis.remote_controle.data.local.ChatBotEntity
import cd.zgeniuscoders.genesis.remote_controle.domain.models.ChatBot

fun ChatBotEntity.toChatBotModel(): ChatBot {
    return ChatBot(id = id, message = message, isRobot = isRobot)
}