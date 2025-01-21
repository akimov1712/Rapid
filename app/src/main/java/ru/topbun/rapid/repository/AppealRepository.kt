package ru.topbun.rapid.repository

import android.content.Context
import ru.topbun.rapid.database.AppDatabase
import ru.topbun.rapid.entity.Appeal

class AppealRepository(private val context: Context) {

    private val dao = AppDatabase.getInstance(context).appealDao()

    fun getAppeals() = dao.getAppeals()
    suspend fun getAppeal(id: Int) = dao.getAppeal(id)
    suspend fun addAppeal(appeal: Appeal) = dao.addAppeal(appeal)


}