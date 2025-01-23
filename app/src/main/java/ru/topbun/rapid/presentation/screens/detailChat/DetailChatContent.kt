package ru.topbun.rapid.presentation.screens.detailChat

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.topbun.rapid.R
import ru.topbun.rapid.entity.Chat
import ru.topbun.rapid.entity.Message
import ru.topbun.rapid.presentation.theme.Colors
import ru.topbun.rapid.presentation.theme.Fonts
import ru.topbun.rapid.presentation.theme.Typography.APP_TEXT
import ru.topbun.rapid.presentation.theme.components.AppTextField
import ru.topbun.rapid.utils.formatDate

data class DetailChatScreen(private val chat: Chat) : Screen {

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val context = LocalContext.current
            val viewModel = rememberScreenModel { DetailChatViewModel(context, chat) }
            Header()
            ChatList(viewModel)
            Spacer(Modifier.height(10.dp))
            FieldSendMessage(viewModel)
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
private fun FieldSendMessage(viewModel: DetailChatViewModel) {
    val state by viewModel.state.collectAsState()
    Box(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
    ){
        AppTextField(
            modifier = Modifier
                .height(48.dp)
                .shadow(2.dp, RoundedCornerShape(8.dp))
                .background(Colors.WHITE),
            value = state.message,
            onValueChange = viewModel::changeMessage,
            placeholder = "Ваш вопрос",
        )
        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp)
                .size(32.dp)
                .clickable {
                    viewModel.addMessage()
                }
                .clip(RoundedCornerShape(4.dp))
                .background(Colors.CYAN, RoundedCornerShape(4.dp))
                .padding(7.dp),
            painter = painterResource(R.drawable.ic_send),
            contentDescription = null,
            tint = Colors.WHITE
        )
    }
}

@Composable
private fun ColumnScope.ChatList(viewModel: DetailChatViewModel) {
    val state by viewModel.state.collectAsState()
    state.userId?.let { userId ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item { QuestionItem(state.chat.quest.title) }
            items(state.chat.messages) { message ->
                MessageItem(message, userId == message.userId)
            }
        }
    }
}

@Composable
private fun MessageItem(message: Message, isSelfMessage: Boolean) {
    val bgCardColor = if (isSelfMessage) Colors.CYAN else Colors.WHITE
    val primaryTextColor = if (isSelfMessage) Colors.WHITE else Colors.BLACK
    val secondaryTextColor = if (isSelfMessage) Colors.WHITE.copy(0.5f) else Colors.GRAY_DARK
    val align = if (isSelfMessage) Alignment.CenterEnd else Alignment.CenterStart
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .align(align)
                .padding(horizontal = 20.dp)
                .widthIn(max = 250.dp)
                .shadow(4.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(bgCardColor)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = message.text,
                style = APP_TEXT,
                fontSize = 15.sp,
                fontFamily = Fonts.SF.MEDIUM,
                color = primaryTextColor
            )
            Text(
                modifier = Modifier.wrapContentWidth(),
                text = message.date.formatDate(),
                style = APP_TEXT,
                fontSize = 10.sp,
                fontFamily = Fonts.SF.REGULAR,
                textAlign = TextAlign.End,
                color = secondaryTextColor
            )
        }
    }
}

@Composable
private fun QuestionItem(quest: String) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(Colors.WHITE)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = "Вопрос",
            style = APP_TEXT,
            fontSize = 12.sp,
            fontFamily = Fonts.SF.REGULAR,
            color = Colors.GRAY_DARK
        )
        Text(
            text = quest,
            style = APP_TEXT,
            fontSize = 14.sp,
            fontFamily = Fonts.SF.MEDIUM,
        )
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