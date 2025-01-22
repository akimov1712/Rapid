package ru.topbun.rapid.presentation.screens.appeals

import android.content.Context
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import ru.topbun.rapid.presentation.theme.components.ScreenModelState
import ru.topbun.rapid.repository.AppealRepository

class AppealViewModel(context: Context): ScreenModelState<AppealState>(AppealState()) {

    private val repository = AppealRepository(context)

    private fun getAppeals() = screenModelScope.launch {
        repository.getAppeals().collect{
            updateState { copy(it) }
        }
    }

    init {
        getAppeals()
    }

}