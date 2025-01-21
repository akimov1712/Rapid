package ru.topbun.rapid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.topbun.rapid.database.dao.AppealDao
import ru.topbun.rapid.database.dao.UserDao
import ru.topbun.rapid.entity.Appeal
import ru.topbun.rapid.entity.User

@Database(
    entities = [
        User::class,
        Appeal::class,
    ],
    exportSchema = true,
    version = 3,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun appealDao(): AppealDao

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