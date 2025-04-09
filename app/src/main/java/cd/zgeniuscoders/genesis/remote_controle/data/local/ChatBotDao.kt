package cd.zgeniuscoders.genesis.remote_controle.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatBotDao {

    @Insert
    suspend fun add(message: ChatBotEntity)

    @Query("SELECT * FROM messages")
    fun all(): Flow<List<ChatBotEntity>>

}