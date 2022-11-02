package com.finance_tracker.finance_tracker.presentation.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.ExpenseIncomeTabs

@Composable
fun AnalyticsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CoinTheme.color.background)
            .statusBarsPadding()
    ) {
        AnalyticsScreenAppBar()

        ExpenseIncomeTabs(
            modifier = Modifier
                .padding(
                    top = 24.dp,
                    start = 20.dp
                )
        )

    }
}