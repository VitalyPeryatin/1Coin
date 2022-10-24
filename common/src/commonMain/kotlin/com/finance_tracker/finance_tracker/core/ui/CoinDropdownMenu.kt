package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
expect fun CoinDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    xOffset: Dp = 0.dp,
    yOffset: Dp = 8.dp,
    content: @Composable ColumnScope.() -> Unit
)

@Composable
expect fun DropdownMenuItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable RowScope.() -> Unit = {}
)