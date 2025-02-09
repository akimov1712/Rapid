package ru.topbun.rapid.presentation.theme.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.topbun.rapid.presentation.theme.Colors
import ru.topbun.rapid.presentation.theme.Fonts
import ru.topbun.rapid.presentation.theme.Typography.APP_TEXT

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    iconButton: (@Composable () -> Unit)? = null,
    padding: PaddingValues = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
    enabled: Boolean = true,
    errorText: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = true,
    borderWidth: Dp = 1.dp,
    textAlignment: Alignment = Alignment.CenterStart,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    borderWidth,
                    Colors.GRAY_LIGHT.takeIf { errorText.isNullOrEmpty() } ?: Colors.RED,
                    RoundedCornerShape(8.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                modifier = modifier
                    .weight(1f)
                    .padding(padding),
                contentAlignment = textAlignment
            ){
                if (value.isEmpty()){
                    Text(
                        text = placeholder,
                        style = APP_TEXT,
                        fontSize = 15.sp,
                        fontFamily = Fonts.SF.MEDIUM,
                        color = Colors.GRAY_DARK
                    )
                }
                val textStyle = TextStyle(
                    color = Colors.BLACK,
                    fontSize = 15.sp,
                    fontFamily = Fonts.SF.MEDIUM
                )
                BasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = value,
                    onValueChange = onValueChange,
                    enabled = enabled,
                    textStyle = textStyle,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    singleLine = singleLine,
                    maxLines = maxLines,
                    minLines = minLines,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    visualTransformation = visualTransformation
                )

            }
            iconButton?.let {
                it()
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
        errorText?.let {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = it,
                fontSize = 13.sp,
                fontFamily = Fonts.SF.SEMI_BOLD,
                color = Colors.RED
            )
        }
    }

}