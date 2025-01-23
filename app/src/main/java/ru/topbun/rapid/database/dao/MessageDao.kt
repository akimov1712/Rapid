package ru.topbun.rapid.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.topbun.rapid.entity.Message

@Dao
interface MessageDao {

    @Query("SElECT * FROM messages WHERE questId = :questId")
    fun getMessageFlow(questId: Int): Flow<List<Message>>

    @Query("SElECT * FROM messages WHERE questId = :questId")
    suspend fun getMessage(questId: Int): List<Message>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMessage(message: Message)

}