package ru.topbun.rapid.presentation.screens.chat

import ru.topbun.rapid.entity.Chat

data class ChatState(
    val chats: List<Chat> = emptyList()
)
