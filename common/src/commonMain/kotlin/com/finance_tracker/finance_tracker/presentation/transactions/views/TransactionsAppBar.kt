package com.finance_tracker.finance_tracker.presentation.transactions.views

import androidx.compose.foundation.background
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter

@Composable
fun TransactionsAppBar(
    modifier: Modifier = Modifier,
    selectedItemsCount: Int = 0,
    onCloseClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    val hasSelectedItems by remember(selectedItemsCount) {
        derivedStateOf { selectedItemsCount > 0 }
    }
    TopAppBar(
        modifier = modifier
            .background(CoinTheme.color.background)
            .statusBarsPadding(),
        title = {
            Text(
                text = if (hasSelectedItems) {
                    stringResource("transactions_title_selected") + selectedItemsCount
                } else {
                    stringResource("transactions_title_normal")
                },
                style = CoinTheme.typography.h4
            )
        },
        navigationIcon = if (hasSelectedItems) {{
            AppBarIcon(
                painter = rememberVectorPainter("ic_close"),
                onClick = onCloseClick,
            )
        }} else null,
        actions = {
            if (hasSelectedItems) {
                AppBarIcon(
                    painter = rememberVectorPainter("ic_delete"),
                    onClick = onDeleteClick,
                    tint = CoinTheme.color.accentRed
                )
            }
        },
        backgroundColor = Color.White
    )
}