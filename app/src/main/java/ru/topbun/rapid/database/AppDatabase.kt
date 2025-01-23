package ru.topbun.rapid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.topbun.rapid.database.dao.AppealDao
import ru.topbun.rapid.database.dao.MessageDao
import ru.topbun.rapid.database.dao.QuestionDao
import ru.topbun.rapid.database.dao.UserDao
import ru.topbun.rapid.entity.Appeal
import ru.topbun.rapid.entity.Message
import ru.topbun.rapid.entity.Question
import ru.topbun.rapid.entity.User

@Database(
    entities = [
        User::class,
        Appeal::class,
        Message::class,
        Question::class,
    ],
    exportSchema = true,
    version = 4,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun appealDao(): AppealDao
    abstract fun questionDao(): QuestionDao
    abstract fun messageDao(): MessageDao

    companion object {

        private const val DB_NAME = "database.db"
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context) = INSTANCE ?: synchronized(Unit) {
            INSTANCE ?: createDatabase(context).also { INSTANCE = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context, AppDatabase::class.java, DB_NAME
        ).build()

    }

}