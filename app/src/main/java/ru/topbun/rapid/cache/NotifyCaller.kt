package ru.topbun.rapid.cache

import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.map

fun Settings.getNotifyEnabled() = this.data.map { it[AppSettings.KEY_NOTIFY_ENABLED]}
suspend fun Settings.setNotifyEnabled(status: Boolean) = this.edit { it[AppSettings.KEY_NOTIFY_ENABLED] = status}