package ru.topbun.rapid.presentation.screens.submit

import android.graphics.BitmapFactory
import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.topbun.pawmate.presentation.theme.Colors
import ru.topbun.pawmate.presentation.theme.Typography.APP_TEXT
import ru.topbun.pawmate.presentation.theme.components.noRippleClickable
import ru.topbun.pawmate.presentation.theme.components.rippleClickable
import ru.topbun.pawmate.utils.pickImageLauncher
import ru.topbun.rapid.R
import ru.topbun.rapid.entity.AppealCategory
import ru.topbun.rapid.presentation.screens.auth.fragments.signUp.SignUpViewModel
import ru.topbun.rapid.presentation.theme.Fonts
import ru.topbun.rapid.presentation.theme.components.AppTextField
import ru.topbun.rapid.presentation.theme.components.PickerDialog

object SubmitScreen: Screen {
    @Composable
    override fun Content() {
        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            val context = LocalContext.current
            val viewModel = rememberScreenModel { SubmitViewModel(context) }
            Header()
            Spacer(Modifier.height(10.dp))
            Fields(viewModel)
        }
    }
}

@Composable
private fun Fields(viewModel: SubmitViewModel) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        FieldTitle(state, viewModel)
        FieldCategory(state, viewModel)
        FieldDescr(state, viewModel)
        PickImage(viewModel, state)

    }
}

@Composable
private fun PickImage(
    viewModel: SubmitViewModel,
    state: SubmitState
) {
    val context = LocalContext.current
    val launcher = pickImageLauncher(context) {
        viewModel.changeImage(it)
    }
    val bitmap = BitmapFactory.decodeFile(state.image)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 200.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Colors.GRAY_LIGHT, RoundedCornerShape(8.dp))
            .rippleClickable { launcher.launch("image/*") },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_camera),
            contentDescription = null,
            tint = Colors.GRAY_LIGHT
        )
        bitmap?.let {
            Image(
                modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                bitmap = it.asImageBitmap(),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun FieldDescr(
    state: SubmitState,
    viewModel: SubmitViewModel
) {
    AppTextField(
        modifier = Modifier.height(120.dp),
        value = state.descr,
        onValueChange = viewModel::changeDescr,
        placeholder = "Текст обращения",
        textAlignment = Alignment.TopStart,
        singleLine = false
    )
}

@Composable
private fun FieldCategory(
    state: SubmitState,
    viewModel: SubmitViewModel
) {
    var openModal by remember { mutableStateOf(false) }
    Box {
        AppTextField(
            modifier = Modifier
                .height(48.dp)
                .noRippleClickable {
                    openModal = true
                },
            value = state.category?.toString() ?: "",
            onValueChange = {},
            placeholder = "Не выбрано",
            enabled = false
        )
        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            painter = painterResource(R.drawable.ic_dropdown),
            contentDescription = null,
            tint = Colors.CYAN
        )
        if (openModal) {
            PickerDialog(
                items = AppealCategory.entries.map { it.toString() },
                onPickItem = {
                    val category = it?.let { AppealCategory.fromString(it) }
                    viewModel.changeCategory(category)
                    openModal = false
                },
                onDismissDialog = { openModal = false }
            )
        }
    }
}

@Composable
private fun FieldTitle(
    state: SubmitState,
    viewModel: SubmitViewModel
) {
    AppTextField(
        modifier = Modifier.height(48.dp),
        value = state.title,
        onValueChange = viewModel::changeTitle,
        placeholder = "Заголовок",
    )
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
            text = "Подача обращения",
            style = APP_TEXT,
            fontSize = 20.sp,
            fontFamily = Fonts.SF.SEMI_BOLD,
        )
    }
}