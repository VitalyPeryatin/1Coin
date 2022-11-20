package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
@Suppress("MissingModifierDefaultValue", "ModifierParameterPosition", "ReusedModifierInstance")
actual fun CoinGridDropdownMenu(
    columnSize: Dp,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier,
    xOffset: Dp,
    yOffset: Dp,
    content: LazyGridScope.() -> Unit
) {
    MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(12.dp))) {
        GridDropdownMenu(
            columnSize = columnSize,
            modifier = modifier,
            expanded = expanded,
            offset = DpOffset(x = xOffset, y = yOffset),
            onDismissRequest = onDismissRequest,
            content = content
        )
    }
}