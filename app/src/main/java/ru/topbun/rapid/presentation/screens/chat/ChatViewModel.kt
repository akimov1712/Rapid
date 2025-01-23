package ru.topbun.rapid.presentation.screens.chat

import android.content.Context
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import ru.topbun.rapid.presentation.theme.components.ScreenModelState
import ru.topbun.rapid.repository.ChatRepository

class ChatViewModel(context: Context): ScreenModelState<ChatState>(ChatState()) {

    private val chatRepository = ChatRepository(context)

    private fun getChats() = screenModelScope.launch {
        chatRepository.getChats().collect{
            updateState { copy(it) }
        }
    }

    init {
        getChats()
    }

}