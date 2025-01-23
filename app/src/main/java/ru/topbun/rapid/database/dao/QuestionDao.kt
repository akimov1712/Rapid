package ru.topbun.rapid.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.topbun.rapid.entity.Question

@Dao
interface QuestionDao {

    @Query("SELECT * FROM questions")
    fun getQuestions(): Flow<List<Question>>

    @Query("SELECT * FROM questions WHERE userId = :userId")
    fun getQuestions(userId: Int): Flow<List<Question>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuestion(quest: Question)

}