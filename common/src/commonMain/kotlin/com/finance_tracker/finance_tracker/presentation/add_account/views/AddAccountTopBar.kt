package com.finance_tracker.finance_tracker.presentation.add_account.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.CoinTopAppBar
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter

@Composable
fun AddAccountTopBar(
    topBarTextId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CoinTopAppBar(
        modifier = modifier,
        navigationIcon = {
            AppBarIcon(
                painter = rememberVectorPainter("ic_arrow_back"),
                onClick = onBackClick
            )
        },
        title = {
            Text(
                text = stringResource(topBarTextId),
                style = CoinTheme.typography.h4
            )
        }
    )
}