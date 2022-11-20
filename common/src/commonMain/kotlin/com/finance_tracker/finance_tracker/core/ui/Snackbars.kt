package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.core.common.imePadding
import com.finance_tracker.finance_tracker.core.common.systemBarsPadding
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

@Composable
fun DefaultSnackbar(
    data: SnackbarData,
    modifier: Modifier = Modifier
) {
    Snackbar(
        modifier = modifier
            .systemBarsPadding()
            .imePadding(),
        actionColor = CoinTheme.color.primary,
        snackbarData = data
    )
}