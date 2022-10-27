package com.finance_tracker.finance_tracker.presentation.add_transaction.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter

@Composable
fun CalendarDayView(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(CoinTheme.color.background)
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CompositionLocalProvider(
            LocalContentColor provides LocalContentColor.current.copy(alpha = 0.8f)
        ) {
            Icon(
                painter = rememberVectorPainter("ic_calendar"),
                contentDescription = null,
                tint = LocalContentColor.current
            )

            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = "Today",
                style = CoinTheme.typography.subtitle2,
                color = LocalContentColor.current
            )
        }
    }
}