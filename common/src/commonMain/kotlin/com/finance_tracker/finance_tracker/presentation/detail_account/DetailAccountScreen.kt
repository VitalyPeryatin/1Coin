package com.finance_tracker.finance_tracker.presentation.detail_account

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.ui.transactions.CommonTransactionsList

@Composable
fun DetailAccountScreen(
    data: DetailAccountData
) {
    StoredViewModel<DetailAccountViewModel> { viewModel ->

        LaunchedEffect(Unit) {
            viewModel.onScreenComposed()
        }

        Column {
            DetailAccountAppBar(
                name = data.name,
                color = data.color
            )

            val transactions by viewModel.transactions.collectAsState()
            CommonTransactionsList(transactions)
        }
    }
}