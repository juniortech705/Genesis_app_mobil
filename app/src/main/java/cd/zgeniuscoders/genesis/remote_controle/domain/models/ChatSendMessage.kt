package cd.zgeniuscoders.genesis.remote_controle.domain.models

data class ChatSendMessage(
    val message: String,
    val isRobot: Boolean = false,
    val isVoiceMessage: Boolean = false
)
