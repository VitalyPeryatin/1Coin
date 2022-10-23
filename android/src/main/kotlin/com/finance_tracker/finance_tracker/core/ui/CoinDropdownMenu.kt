package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun CoinDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    xOffset: Dp = 0.dp,
    yOffset: Dp = 8.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(12.dp))) {
        DropdownMenu(
            modifier = modifier,
            expanded = expanded,
            offset = DpOffset(x = xOffset, y = yOffset),
            onDismissRequest = onDismissRequest,
            content = content
        )
    }
}

@Preview
@Composable
fun CoinDropdownMenuPreview() {
    CoinDropdownMenu(expanded = true, onDismissRequest = {}, content = {})
}