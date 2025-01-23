package ru.topbun.rapid.repository

import android.content.Context
import ru.topbun.rapid.cache.AppSettings.dataStore
import ru.topbun.rapid.database.AppDatabase
import ru.topbun.rapid.entity.Message
import ru.topbun.rapid.entity.Question

class MessageRepository(context: Context) {

    private val dao = AppDatabase.getInstance(context).messageDao()
    private val settings = context.dataStore

    fun getMessageFlow(questId: Int) = dao.getMessageFlow(questId)
    suspend fun getMessage(questId: Int) = dao.getMessage(questId)
    suspend fun addMessage(message: Message) = dao.addMessage(message)

}