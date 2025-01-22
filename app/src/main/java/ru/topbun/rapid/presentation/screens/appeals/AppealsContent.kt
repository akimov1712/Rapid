package ru.topbun.rapid.presentation.screens.appeals

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImagePainter.State.Empty.painter
import ru.topbun.pawmate.presentation.theme.Colors
import ru.topbun.pawmate.presentation.theme.Typography.APP_TEXT
import ru.topbun.pawmate.presentation.theme.components.rippleClickable
import ru.topbun.rapid.entity.Appeal
import ru.topbun.rapid.entity.AppealStatus
import ru.topbun.rapid.entity.FAQ
import ru.topbun.rapid.presentation.screens.submit.IntentOpenSubmit
import ru.topbun.rapid.presentation.screens.submit.SubmitScreen
import ru.topbun.rapid.presentation.theme.Fonts
import ru.topbun.rapid.repository.AppealRepository
import ru.topbun.rapid.utils.formatDate
import ru.topbun.rapid.utils.getAddressFromLocation

object AppealsScreen: Screen {
    @Composable
    override fun Content() {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val context = LocalContext.current
            val viewModel = rememberScreenModel { AppealViewModel(context) }
            val state by viewModel.state.collectAsState()
            Header()
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items = state.appeals){ AppealItem(appeal = it) }
                item { Spacer(Modifier.height(10.dp)) }
            }
        }
    }

}

@Composable
private fun AppealItem(appeal: Appeal) {
    val navigator = LocalNavigator.currentOrThrow
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .background(Colors.WHITE)
            .clip(RoundedCornerShape(8.dp))
            .rippleClickable {
                if (appeal.status == AppealStatus.Progress){
                    navigator.push(SubmitScreen(IntentOpenSubmit.Edit(appeal)))
                }
            },
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val bitmap = BitmapFactory.decodeFile(appeal.image)
        bitmap?.let {
            Image(
                modifier = Modifier
                    .weight(2f)
                    .aspectRatio(1.2f)
                    .clip(RoundedCornerShape(8.dp)),
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .weight(3f)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = appeal.title,
                style = APP_TEXT,
                fontSize = 18.sp,
                fontFamily = Fonts.SF.SEMI_BOLD,
            )
            val color = when(appeal.status){
                AppealStatus.Progress -> Colors.GRAY_DARK
                AppealStatus.Success -> Colors.GREEN
                AppealStatus.Failed -> Colors.RED
            }
            Text(
                text = appeal.status.toString(),
                style = APP_TEXT,
                fontSize = 15.sp,
                fontFamily = Fonts.SF.SEMI_BOLD,
                color = color
            )
            Text(
                text = appeal.date.formatDate(),
                style = APP_TEXT,
                fontSize = 15.sp,
                fontFamily = Fonts.SF.MEDIUM,
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
            text = "Обращения",
            style = APP_TEXT,
            fontSize = 20.sp,
            fontFamily = Fonts.SF.SEMI_BOLD,
        )
    }
}