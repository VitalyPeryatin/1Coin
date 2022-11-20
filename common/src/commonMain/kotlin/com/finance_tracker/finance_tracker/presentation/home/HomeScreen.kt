package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.systemBarsPadding
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.TitleHeader
import com.finance_tracker.finance_tracker.core.ui.transactions.CommonTransactionsList
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun HomeScreen() {
    StoredViewModel<HomeViewModel> { viewModel ->
        val accounts by viewModel.accounts.collectAsState()
        val transactions by viewModel.transactions.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.onScreenComposed()
        }

        val accountsLazyListState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        LaunchedEffect(Unit) {
            viewModel.events
                .filterNotNull()
                .onEach { event ->
                    handleEvent(
                        event,
                        coroutineScope,
                        accountsLazyListState
                    )
                }
                .launchIn(this)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CoinTheme.color.background)
                .systemBarsPadding()
        ) {

            val totalAmount by viewModel.totalAmount.collectAsState()
            val totalCurrency by viewModel.totalCurrency.collectAsState()
            HomeTopBar(
                totalAmount = totalAmount,
                totalCurrency = totalCurrency
            )

            TitleHeader(
                modifier = Modifier
                    .padding(top = 26.dp),
                text = "home_my_accounts"
            )

            AccountsWidget(
                data = accounts,
                state = accountsLazyListState
            )

            TitleHeader(
                modifier = Modifier
                    .padding(top = 26.dp),
                text = "home_last_transaction"
            )

            CommonTransactionsList(transactions = transactions.takeLast(3))
        }
    }
}