package ru.topbun.rapid.presentation.screens.settings

import android.content.Context
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import ru.topbun.rapid.entity.Question
import ru.topbun.rapid.presentation.theme.components.ScreenModelState
import ru.topbun.rapid.repository.NotifyRepository
import ru.topbun.rapid.repository.QuestionRepository
import ru.topbun.rapid.repository.UserRepository

class SettingsViewModel(context: Context): ScreenModelState<SettingsState>(SettingsState()) {

    private val notifyRepository = NotifyRepository(context)
    private val userRepository = UserRepository(context)
    private val questRepository = QuestionRepository(context)

    fun changeNotifyEnabled(status: Boolean) = screenModelScope.launch {
        notifyRepository.setNotifyEnabled(status)
    }

    fun addQuestion() = screenModelScope.launch {
        val userId = userRepository.getUserInfo() ?: return@launch
        val quest = Question(
            userId = userId.id,
            title = stateValue.feedbackMessage
        )
        questRepository.addQuest(quest)
        updateState { copy(feedbackMessage = "") }
    }

    fun changeFeedbackMessage(msg: String) = updateState { copy(feedbackMessage = msg) }

    private fun getNotifyEnabled() = screenModelScope.launch {
        notifyRepository.getNotifyEnabled().collect{
            updateState { copy(notifyEnabled = it ?: false) }
        }
    }

    init {
        getNotifyEnabled()
    }

}