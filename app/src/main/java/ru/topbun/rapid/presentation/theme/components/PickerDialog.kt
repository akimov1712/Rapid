package ru.topbun.rapid.presentation.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.topbun.pawmate.presentation.theme.Colors
import ru.topbun.pawmate.presentation.theme.Typography.APP_TEXT
import ru.topbun.rapid.presentation.theme.Fonts

@Composable
fun PickerDialog(
    items: List<String>,
    onPickItem: (String?) -> Unit,
    onDismissDialog: () -> Unit
) {
    val items = listOf(null) + items
    DialogWrapper(
        onDismissDialog = onDismissDialog
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(items){
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .heightIn(min = 48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Colors.CYAN)
                        .clickable { onPickItem(it) },
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = it ?: "Не выбрано",
                        style = APP_TEXT,
                        fontSize = 16.sp,
                        fontFamily = Fonts.SF.SEMI_BOLD,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        color = Colors.WHITE
                    )
                }
            }
        }
    }
}