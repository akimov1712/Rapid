package ru.topbun.rapid.presentation.screens.settings

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.topbun.pawmate.presentation.theme.components.AppButton
import ru.topbun.pawmate.presentation.theme.components.noRippleClickable
import ru.topbun.rapid.R
import ru.topbun.rapid.presentation.theme.Colors
import ru.topbun.rapid.presentation.theme.Typography.APP_TEXT
import ru.topbun.rapid.entity.FAQ
import ru.topbun.rapid.presentation.screens.chat.ChatScreen
import ru.topbun.rapid.presentation.theme.Fonts
import ru.topbun.rapid.presentation.theme.components.AppTextField

object SettingsScreen: Screen {
    
    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            val context = LocalContext.current
            val viewModel = rememberScreenModel { SettingsViewModel(context) }
            Header()
            Notify(viewModel)
            Spacer(Modifier.height(20.dp))
            Tips()
            Spacer(Modifier.height(20.dp))
            Feedback(viewModel)
        }
    }
}

@Composable
private fun Feedback(viewModel: SettingsViewModel) {
    val state by viewModel.state.collectAsState()
    val navigator = LocalNavigator.currentOrThrow
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Обратная связь",
            style = APP_TEXT,
            fontSize = 14.sp,
            fontFamily = Fonts.SF.REGULAR,
            color = Colors.GRAY_DARK
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            Box{
                AppTextField(
                    modifier = Modifier
                        .height(120.dp)
                        .shadow(2.dp, RoundedCornerShape(8.dp))
                        .background(Colors.WHITE),
                    value = state.feedbackMessage,
                    onValueChange = viewModel::changeFeedbackMessage,
                    placeholder = "Ваш вопрос",
                    textAlignment = Alignment.TopStart,
                    singleLine = false
                )
                Icon(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .size(32.dp)
                        .clickable {
                            if (state.feedbackMessage.isNotEmpty()) {
                                viewModel.addQuestion()
                                Toast.makeText(context, "Вопрос добален", Toast.LENGTH_SHORT).show()
                            }
                        }
                        .clip(RoundedCornerShape(4.dp))
                        .background(Colors.CYAN, RoundedCornerShape(4.dp))
                        .padding(7.dp),
                    painter = painterResource(R.drawable.ic_send),
                    contentDescription = null,
                    tint = Colors.WHITE
                )
            }
            AppButton(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .height(48.dp)
                    .fillMaxWidth(),
                text = "Мои вопросы",
            ) {
               navigator.push(ChatScreen)
            }
        }
    }
}

@Composable
private fun Tips() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        var selectedItemIndex by remember { mutableStateOf<Int?>(null) }
        Text(
            text = "Справочная информация (FAQ)",
            style = APP_TEXT,
            fontSize = 14.sp,
            fontFamily = Fonts.SF.REGULAR,
            color = Colors.GRAY_DARK
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            faqs.forEachIndexed { index, item ->
                TipItem(item, index == selectedItemIndex){
                    selectedItemIndex = if (index == selectedItemIndex) null else index
                }
            }
        }
    }
}

@Composable
private fun TipItem(tip:FAQ, active: Boolean, onClickItem: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(Colors.WHITE)
            .noRippleClickable { onClickItem() }
            .animateContentSize(tween(300))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier.weight(1f),
                text = tip.question,
                style = APP_TEXT,
                fontSize = 16.sp,
                fontFamily = Fonts.SF.SEMI_BOLD
            )
            val animateRotationDeg by animateFloatAsState( if(active) 180f else 0f, tween(300) )
            Icon(
                modifier = Modifier.rotate(animateRotationDeg),
                painter = painterResource(R.drawable.ic_dropdown),
                contentDescription = null,
                tint = Colors.CYAN
            )
        }
        if (active){
            Box(Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Colors.GRAY_LIGHT))
            Text(
                text = tip.answer,
                style = APP_TEXT,
                fontSize = 12.sp,
                fontFamily = Fonts.SF.REGULAR,
                color = Colors.GRAY_DARK
            )
        }

    }
}


@Composable
private fun Notify(viewModel: SettingsViewModel) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Уведомления",
            style = APP_TEXT,
            fontSize = 14.sp,
            fontFamily = Fonts.SF.REGULAR,
            color = Colors.GRAY_DARK
        )
        NotifyItem(state){
            viewModel.changeNotifyEnabled(it)
        }
    }
}

@Composable
private fun NotifyItem(
    state: SettingsState,
    onChangeStatus: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(Colors.WHITE)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = "Изменение статуса",
                style = APP_TEXT,
                fontSize = 18.sp,
                fontFamily = Fonts.SF.SEMI_BOLD,
            )
            Text(
                text = "После проверки обращения, модератор изменит статус и вам придет уведомление.",
                style = APP_TEXT,
                fontSize = 14.sp,
                fontFamily = Fonts.SF.REGULAR,
                color = Colors.GRAY_DARK
            )
        }
        Switch(
            checked = state.notifyEnabled,
            onCheckedChange = { onChangeStatus(!state.notifyEnabled) },
            modifier = Modifier.padding(10.dp),
            colors = SwitchDefaults.colors(
                checkedTrackColor = Colors.CYAN,
                uncheckedThumbColor = Colors.CYAN,
                uncheckedTrackColor = Colors.WHITE,
                uncheckedBorderColor = Colors.CYAN
            )
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
            text = "Настройки",
            style = APP_TEXT,
            fontSize = 20.sp,
            fontFamily = Fonts.SF.SEMI_BOLD,
        )
    }
}

private val faqs = listOf(
    FAQ(
        "Как подать обращение?",
        "Перейдите в раздел \"Создать обращение\", введите заголовок, текст обращения, выберите категорию проблемы, добавьте фото (опционально) и укажите геолокацию на карте. Затем нажмите \"Отправить\"."
    ),
    FAQ(
        "Как проверить статус моего обращения?",
        "В разделе \"Мои обращения\" вы найдете список всех поданных заявок. Для каждого обращения отображается текущий статус: \"В процессе\", \"Решено\" или \"Отклонено\"."
    ),
    FAQ(
        "Можно ли изменить или удалить уже поданное обращение?",
        "Да, вы можете редактировать или удалить обращение, пока оно находится в статусе \"В процессе\". Перейдите в раздел \"Мои обращения\", выберите нужное и нажмите на кнопку \"Редактировать\" или \"Удалить\"."
    ),
    FAQ(
        "Как включить или отключить уведомления?",
        "Перейдите в настройки приложения, найдите раздел \"Уведомления\" и включите/отключите уведомления, используя переключатель."
    ),
    FAQ(
        "Как связаться с администрацией?",
        "В разделе \"Настройки\" в самом низу, будет форма для связи с адмиинстрацией. Заполните форму и нажмите кнопку отправить. Ответ можете проверить в разделе \"Мои вопросы\""
    ),
)