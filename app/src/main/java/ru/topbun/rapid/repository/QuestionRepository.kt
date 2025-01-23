package ru.topbun.rapid.repository

import android.content.Context
import ru.topbun.rapid.cache.AppSettings.dataStore
import ru.topbun.rapid.cache.getUserId
import ru.topbun.rapid.database.AppDatabase
import ru.topbun.rapid.entity.Question

class QuestionRepository(context: Context) {

    private val dao = AppDatabase.getInstance(context).questionDao()
    private val settings = context.dataStore

    suspend fun getQuestionForUser() = settings.getUserId()?.let { dao.getQuestions(it) } ?: throw RuntimeException("User not auth")
    fun getQuestion() = dao.getQuestions()
    suspend fun addQuest(quest: Question) = dao.addQuestion(quest)

}