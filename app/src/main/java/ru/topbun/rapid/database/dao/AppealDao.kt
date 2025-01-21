package ru.topbun.rapid.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.topbun.rapid.entity.Appeal

@Dao
interface AppealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAppeal(appeal: Appeal)

    @Query("SELECT * FROM appeals")
    fun getAppeals(): Flow<List<Appeal>>

    @Query("SELECT * FROM appeals WHERE id = :id")
    suspend fun getAppeal(id: Int): Appeal

}