package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

@Composable
fun CoinWidget(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(
                top = 24.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp,
            )
    ) {
        Text(
            text = title,
            style = CoinTheme.typography.h5
        )

        Box(
            modifier = Modifier
                .padding(top = 12.dp)
                .border(
                    width = 1.dp,
                    color = CoinTheme.color.dividers,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            content.invoke()
        }
    }
}