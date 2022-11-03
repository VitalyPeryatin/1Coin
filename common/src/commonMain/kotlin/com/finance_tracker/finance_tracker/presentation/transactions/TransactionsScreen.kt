package com.finance_tracker.finance_tracker.presentation.transactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.ui.transactions.CommonTransactionsList
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.TransactionItem
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.finance_tracker.finance_tracker.presentation.transactions.views.TransactionsAppBar

@Composable
fun TransactionsScreen() {
    StoredViewModel<TransactionsViewModel> { viewModel ->
        LaunchedEffect(Unit) {
            viewModel.loadTransactions()
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TransactionsAppBar()

            val transactions by viewModel.transactions.collectAsState()
            CommonTransactionsList(transactions)
        }
    }
}