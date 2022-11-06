package com.finance_tracker.finance_tracker.presentation.transactions.views

import androidx.compose.foundation.background
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

@Composable
fun TransactionsAppBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier
            .background(CoinTheme.color.background)
            .statusBarsPadding(),
        title = {
            Text(
                text = stringResource("transactions_title"),
                style = CoinTheme.typography.h4
            )
        },
        backgroundColor = Color.White
    )
}