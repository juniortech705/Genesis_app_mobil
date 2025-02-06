package cd.zgeniuscoders.genesis.remote_controle.data.mappers

import cd.zgeniuscoders.genesis.remote_controle.data.dto.SpeechRecognitionDto
import cd.zgeniuscoders.genesis.remote_controle.domain.models.Speech

fun SpeechRecognitionDto.toSpeechModel(): Speech {
    return Speech(
        message = speech.message
    )
}