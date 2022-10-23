package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun AppBarIcon(
    painter: Painter,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    contentDescription: String? = null,
    tint: Color = Color.Black
) {
    Icon(
        modifier = modifier
            .clip(CircleShape)
            .size(40.dp)
            .clickable { onClick.invoke() }
            .padding(8.dp),
        painter = painter,
        contentDescription = contentDescription,
        tint = tint
    )
}