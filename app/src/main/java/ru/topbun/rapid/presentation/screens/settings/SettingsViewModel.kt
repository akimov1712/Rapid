package ru.topbun.rapid.presentation.screens.settings

import android.content.Context
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import ru.topbun.rapid.presentation.theme.components.ScreenModelState
import ru.topbun.rapid.repository.NotifyRepository

class SettingsViewModel(context: Context): ScreenModelState<SettingsState>(SettingsState()) {

    private val notifyRepository = NotifyRepository(context)

    fun changeNotifyEnabled(status: Boolean) = screenModelScope.launch {
        notifyRepository.setNotifyEnabled(status)
    }

    private fun getNotifyEnabled() = screenModelScope.launch {
        notifyRepository.getNotifyEnabled().collect{
            updateState { copy(notifyEnabled = it ?: false) }
        }
    }

    init {
        getNotifyEnabled()
    }

}