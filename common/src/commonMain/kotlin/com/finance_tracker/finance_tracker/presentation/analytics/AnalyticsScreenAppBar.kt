package com.finance_tracker.finance_tracker.presentation.analytics

import androidx.compose.foundation.background
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

@Composable
fun AnalyticsScreenAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier
            .background(CoinTheme.color.background),
        title = {
            Text(
                text = stringResource("tab_analytics"),
                style = CoinTheme.typography.h4
            )
        },
        backgroundColor = CoinTheme.color.background
    )
}