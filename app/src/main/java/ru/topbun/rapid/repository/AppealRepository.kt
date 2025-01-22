package ru.topbun.rapid.repository

import android.content.Context
import ru.topbun.rapid.database.AppDatabase
import ru.topbun.rapid.entity.Appeal

class AppealRepository(private val context: Context) {

    private val dao = AppDatabase.getInstance(context).appealDao()

    fun getAppeals() = dao.getAppeals()
    suspend fun addAppeal(appeal: Appeal) = dao.addAppeal(appeal)
    suspend fun deleteAppeal(id: Int) = dao.deleteAppeal(id)


}