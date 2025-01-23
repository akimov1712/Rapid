package ru.topbun.rapid.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.topbun.rapid.entity.Chat

class ChatRepository(context: Context) {

    private val questionRepository = QuestionRepository(context)
    private val messageRepository = MessageRepository(context)

    suspend fun getChats(): Flow<List<Chat>> {
        return questionRepository.getQuestionForUser().map {
            it.map { quest ->
                val messages = messageRepository.getMessage(quest.id)
                Chat(
                    quest = quest,
                    messages = messages
                )
            }
        }
    }

}