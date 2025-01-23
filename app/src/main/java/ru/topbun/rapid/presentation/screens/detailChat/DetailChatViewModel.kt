package ru.topbun.rapid.presentation.screens.detailChat

import android.content.Context
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.core.registry.screenModule
import kotlinx.coroutines.launch
import ru.topbun.rapid.entity.Chat
import ru.topbun.rapid.entity.Message
import ru.topbun.rapid.presentation.theme.components.ScreenModelState
import ru.topbun.rapid.repository.ChatRepository
import ru.topbun.rapid.repository.MessageRepository
import ru.topbun.rapid.repository.UserRepository

class DetailChatViewModel(context: Context, chat: Chat): ScreenModelState<DetailChatState>(DetailChatState(chat)) {

    private val userRepository = UserRepository(context)
    private val messageRepository = MessageRepository(context)

    private fun loadNewMessage() = screenModelScope.launch {
        messageRepository.getMessageFlow(stateValue.chat.quest.id).collect{
            updateState { copy(chat.copy(messages = it)) }
        }
    }

    fun addMessage() = screenModelScope.launch {
        val userId = stateValue.userId
        if (userId != null && stateValue.message.isNotBlank()){
            val message = Message(
                questId = stateValue.chat.quest.id,
                userId = userId,
                text = stateValue.message
            )
            messageRepository.addMessage(message)
            updateState { copy(message = "") }
        }
    }

    fun changeMessage(msg: String) = updateState { copy(message = msg) }

    private fun loadUserId() = screenModelScope.launch {
        val user = userRepository.getUserInfo()
        user?.let {
            updateState { copy(userId = user.id) }
        }
    }

    init {
        loadUserId()
        loadNewMessage()
    }

}