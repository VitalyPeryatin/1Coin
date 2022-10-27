package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

@Composable
fun CoinOutlinedTextField(
    value: String = "",
    onValueChange: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    charsLimit: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        onValueChange = {
            if (it.length <= charsLimit) {
                onValueChange.invoke(it)
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = CoinTheme.color.primary,
            focusedBorderColor = CoinTheme.color.primary,
            unfocusedBorderColor = CoinTheme.color.content.copy(alpha = 0.2f),
            focusedLabelColor = CoinTheme.color.primary,
            unfocusedLabelColor = CoinTheme.color.content.copy(alpha = 0.2f),
            placeholderColor = CoinTheme.color.content.copy(alpha = 0.2f),
            disabledBorderColor = CoinTheme.color.content.copy(alpha = 0.2f),
            disabledPlaceholderColor = CoinTheme.color.content.copy(alpha = 0.2f),
            disabledLabelColor = CoinTheme.color.content.copy(alpha = 0.2f)
        ),
        shape = RoundedCornerShape(12.dp),
        maxLines = maxLines,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine
    )
}

@Composable
fun CoinOutlinedSelectTextField(
    value: String = "",
    onValueChange: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    maxLines: Int = 1,
    selected: Boolean = false
) {
    val isEmpty = value.isBlank()
    val color = if (selected) {
        CoinTheme.color.primary
    } else {
        CoinTheme.color.content.copy(alpha = 0.2f)
    }
    val labelColor = if (selected && !isEmpty) {
        CoinTheme.color.primary
    } else {
        CoinTheme.color.content.copy(alpha = 0.2f)
    }
    val textColor = CoinTheme.color.content
    ClickableOutlinedTextField(
        modifier = modifier,
        value = value,
        label = label,
        placeholder = placeholder,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = CoinTheme.color.primary,
            focusedBorderColor = color,
            unfocusedBorderColor = color,
            focusedLabelColor = labelColor,
            unfocusedLabelColor = labelColor,
            placeholderColor = color,
            disabledBorderColor = color,
            disabledPlaceholderColor = color,
            disabledLabelColor = labelColor,
            textColor = textColor,
            disabledTextColor = textColor
        ),
        shape = RoundedCornerShape(12.dp),
        maxLines = maxLines,
        leadingIcon = leadingIcon,
        trailingIcon = {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = rememberVectorPainter("ic_expand_more"),
                contentDescription = null,
                tint = CoinTheme.color.content
            )
        },
        borderThickness = if (selected) 2.dp else 1.dp
    )
}

@Composable
private fun ClickableOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
    borderThickness: Dp = 1.dp
) {
    val enabled = false
    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse {
        colors.textColor(enabled).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    @OptIn(ExperimentalMaterialApi::class)
    (BasicTextField(
        value = value,
        modifier = if (label != null) {
            modifier
                .semantics(mergeDescendants = true) {}
                .padding(top = OutlinedTextFieldTopPadding)
        } else {
            modifier
        }
            .background(colors.backgroundColor(enabled).value, shape)
            .defaultMinSize(
                minWidth = TextFieldDefaults.MinWidth,
                minHeight = TextFieldDefaults.MinHeight
            ),
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(colors.cursorColor(isError).value),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.OutlinedTextFieldDecorationBox(
                value = value,
                visualTransformation = visualTransformation,
                innerTextField = innerTextField,
                placeholder = placeholder,
                label = label,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                singleLine = singleLine,
                enabled = enabled,
                isError = isError,
                interactionSource = interactionSource,
                colors = colors,
                border = {
                    TextFieldDefaults.BorderBox(
                        enabled = enabled,
                        isError = isError,
                        interactionSource = interactionSource,
                        colors = colors,
                        shape = shape,
                        focusedBorderThickness = borderThickness,
                        unfocusedBorderThickness = borderThickness
                    )
                }
            )
        }
    ))
}

internal val OutlinedTextFieldTopPadding = 8.dp