package cd.zgeniuscoders.genesis.remote_controle.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ChatBotEntity::class], version = 1)
abstract class ChatBotDatabase : RoomDatabase(){
    abstract fun ChatBotDao(): ChatBotDao
}