package cd.zgeniuscoders.genesis.remote_controle.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cd.zgeniuscoders.genesis.remote_controle.domain.models.ChatBot
import cd.zgeniuscoders.genesis.remote_controle.domain.models.ChatSendMessage

@Entity(tableName = "messages")
data class ChatBotEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "message")
    val message: String,
    @ColumnInfo(name = "is_robot")
    val isRobot: Boolean,
) {

    companion object {
        fun fromModel(data: ChatSendMessage): ChatBotEntity {
            return data.run {
                ChatBotEntity(message = message, isRobot = isRobot)
            }
        }
    }

    fun toModel(): ChatBot {
        return ChatBot(id, message, isRobot)
    }
}