package ru.topbun.rapid.presentation.screens.detailChat

import ru.topbun.rapid.entity.Chat
import ru.topbun.rapid.entity.Question

data class DetailChatState(
    val chat: Chat,
    val userId: Int? = null,
    val message: String = ""
)
