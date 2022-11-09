package com.finance_tracker.finance_tracker.presentation.transactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
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


            val lazyTransactionList = viewModel
            //val transactions: LazyPagingItems<>
            //CommonTransactionsList(transactions)
        }
    }
}