package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.ui.AccountCard
import com.finance_tracker.finance_tracker.domain.models.Account

@Composable
fun AccountsWidget(
    modifier: Modifier = Modifier,
    data: List<Account>
) {

    LazyRow(
        modifier = modifier
            .padding(top = 12.dp),
        contentPadding = PaddingValues(start = 4.dp)
    ) {
        items(data) { account ->
            AccountCard(data = account)
        }
    }
}