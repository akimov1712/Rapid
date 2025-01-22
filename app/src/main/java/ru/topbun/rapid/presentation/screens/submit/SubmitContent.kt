package ru.topbun.rapid.presentation.screens.submit

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Parcelable
import android.view.MotionEvent
import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.parcelize.Parcelize
import ru.topbun.pawmate.presentation.theme.Colors
import ru.topbun.pawmate.presentation.theme.Typography.APP_TEXT
import ru.topbun.pawmate.presentation.theme.components.AppButton
import ru.topbun.pawmate.presentation.theme.components.noRippleClickable
import ru.topbun.pawmate.presentation.theme.components.rippleClickable
import ru.topbun.pawmate.utils.pickImageLauncher
import ru.topbun.rapid.R
import ru.topbun.rapid.entity.AppealCategory
import ru.topbun.rapid.presentation.theme.Fonts
import ru.topbun.rapid.presentation.theme.components.AppTextField
import ru.topbun.rapid.presentation.theme.components.PickerDialog
import ru.topbun.rapid.utils.getUserLocation

@Parcelize
class SubmitScreen(
    private val intent: IntentOpenSubmit = IntentOpenSubmit.Append
): Screen, Parcelable {

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = rememberScreenModel { SubmitViewModel(context, intent) }
        val state by viewModel.state.collectAsState()
        LaunchedEffect(state.shouldCloseScreen) {
            if (state.shouldCloseScreen){
                Toast.makeText(context, "Обращение  отправлено", Toast.LENGTH_SHORT).show()
                navigator.pop()
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    state = rememberScrollState(),
                    enabled = state.scrollEnabled
                )
        ) {
            Header(viewModel)
            Spacer(Modifier.height(10.dp))
            Fields(viewModel)
            Spacer(Modifier.height(20.dp))
            Button(state){
                viewModel.addAppeal()
            }
        }
    }

    @Composable
    private fun Button(state: SubmitState, onClickButton: () -> Unit) {
        val validFields = state.fieldsValid
        val title = when(state.intent){
            IntentOpenSubmit.Append -> "Отправить обращение"
            is IntentOpenSubmit.Edit -> "Редактировать обращение"
        }
        AppButton(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .height(48.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = title,
            enabled = validFields
        ) {
            onClickButton()
        }
    }
}

@Composable
private fun Fields(viewModel: SubmitViewModel) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        FieldTitle(state, viewModel)
        FieldCategory(state, viewModel)
        FieldDescr(state, viewModel)
        PickImage(state, viewModel)
        GoogleMapScreen(state, viewModel)
    }
}

@SuppressLint("MissingPermission")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GoogleMapScreen(state: SubmitState, viewModel: SubmitViewModel) {
    var initialPosition = LatLng(55.7558, 37.6173)
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialPosition, 1f)
    }
    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            viewModel.changeScrollState(true)
        }
    }

    LaunchedEffect(Unit) {
        getUserLocation(context){ lat, lon ->
            cameraPositionState.position = CameraPosition.fromLatLngZoom(LatLng(lat, lon), 10f)
        }
    }


    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.3f)
            .clip(RoundedCornerShape(8.dp))
            .pointerInteropFilter { event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        viewModel.changeScrollState(false)
                        false
                    }

                    else -> true
                }
            },
        cameraPositionState = cameraPositionState,
        onMapClick = { latLng ->
            viewModel.changeMapPoint(latLng.latitude, latLng.longitude)
        }
    ) {
        if (state.lat != null && state.lon != null){
            Marker(
                state = MarkerState(position = LatLng(state.lat, state.lon)),
                title = "Проблема",
                snippet = state.title
            )
        }
    }
}


@Composable
private fun PickImage(
    state: SubmitState,
    viewModel: SubmitViewModel,
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
                contentScale = ContentScale.FillWidth,
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
private fun Header(
    viewModel: SubmitViewModel
) {
    val state by viewModel.state.collectAsState()
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
        val intent = state.intent
        val title = when(state.intent){
            IntentOpenSubmit.Append -> "Подать обращения"
            is IntentOpenSubmit.Edit -> "Редактировать обращение"
        }
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = APP_TEXT,
            fontSize = 20.sp,
            fontFamily = Fonts.SF.SEMI_BOLD,
        )
        if (intent is IntentOpenSubmit.Edit){
            IconButton(onClick = {
                viewModel.deleteAppeal()
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Colors.RED
                )
            }
        }
    }
}