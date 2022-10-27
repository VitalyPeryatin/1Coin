package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter

@Composable
fun MyAccountsHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(
                start = 16.dp,
                end = 16.dp
            )
    ) {
        Text(
            text = stringResource("home_my_accounts"),
            style = CoinTheme.typography.h4
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = rememberVectorPainter("ic_arrow_next_small"),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterVertically)
        )
    }
}