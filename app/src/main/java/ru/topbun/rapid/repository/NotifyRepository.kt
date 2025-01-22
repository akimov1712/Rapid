package ru.topbun.rapid.repository

import android.content.Context
import ru.topbun.rapid.cache.AppSettings.dataStore
import ru.topbun.rapid.cache.getNotifyEnabled
import ru.topbun.rapid.cache.setNotifyEnabled
import ru.topbun.rapid.database.AppDatabase

class NotifyRepository(context: Context) {

    private val settings = context.dataStore

    fun getNotifyEnabled() = settings.getNotifyEnabled()
    suspend fun setNotifyEnabled(status: Boolean) = settings.setNotifyEnabled(status)

}