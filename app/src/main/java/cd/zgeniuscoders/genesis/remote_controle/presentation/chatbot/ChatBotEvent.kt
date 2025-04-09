package cd.zgeniuscoders.genesis.remote_controle.presentation.chatbot

sealed interface ChatBotEvent {

    data object OnStartChat: ChatBotEvent
    data object OnSendMessageButtonClick: ChatBotEvent

    data class OnMessageFieldChange(val message: String): ChatBotEvent
}