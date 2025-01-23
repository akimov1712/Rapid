package ru.topbun.rapid.presentation.screens.chat

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.topbun.pawmate.presentation.theme.components.noRippleClickable
import ru.topbun.rapid.entity.Chat
import ru.topbun.rapid.presentation.screens.detailChat.DetailChatScreen
import ru.topbun.rapid.presentation.theme.Colors
import ru.topbun.rapid.presentation.theme.Fonts
import ru.topbun.rapid.presentation.theme.Typography.APP_TEXT
import ru.topbun.rapid.utils.formatDate

object ChatScreen: Screen {

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val context = LocalContext.current
            val viewModel = rememberScreenModel { ChatViewModel(context) }
            val state by viewModel.state.collectAsState()
            Header()
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(state.chats.reversed()){
                    ChatItem(it)
                }
            }
        }
    }
}

@Composable
private fun ChatItem(chat: Chat) {
    val navigator = LocalNavigator.currentOrThrow
    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(Colors.WHITE)
            .noRippleClickable { navigator.push(DetailChatScreen(chat)) }
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = chat.quest.title,
            style = APP_TEXT,
            fontSize = 18.sp,
            fontFamily = Fonts.SF.SEMI_BOLD,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        chat.messages.lastOrNull()?.let {
            Text(
                text = it.text,
                style = APP_TEXT,
                fontSize = 13.sp,
                fontFamily = Fonts.SF.REGULAR,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                color = Colors.GRAY_DARK
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = it.date.formatDate(),
                style = APP_TEXT,
                fontSize = 10.sp,
                fontFamily = Fonts.SF.REGULAR,
                textAlign = TextAlign.End,
                color = Colors.GRAY_DARK
            )
        }
    }
}

@Composable
private fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val navigator = LocalNavigator.currentOrThrow
        IconButton(
            onClick = { navigator.pop() }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Colors.BLACK
            )
        }
        Spacer(Modifier.width(10.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = "Мои вопросы",
            style = APP_TEXT,
            fontSize = 20.sp,
            fontFamily = Fonts.SF.SEMI_BOLD,
        )
    }
}