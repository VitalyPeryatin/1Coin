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
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.finance_tracker.finance_tracker.core.ui.TitleHeader

@Composable
fun HomeScreen() {
    StoredViewModel<HomeViewModel> { viewModel ->
        val accounts by viewModel.accounts.collectAsState()

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
                .statusBarsPadding()
        ) {

            HomeTopBar()

        TitleHeader(
            modifier = Modifier
                .padding(top = 26.dp),
            textValue = "home_my_accounts"
        )
            MyAccountsHeader(
                modifier = Modifier
                    .padding(top = 26.dp),
            )

            AccountsWidget(
                data = accounts,
                state = accountsLazyListState
            )
        }
        AccountsWidget(data = accounts)

        TitleHeader(
            modifier = Modifier
                .padding(top = 26.dp),
            textValue = "home_last_transaction"

        )

        LastTransactionWidget()

    }
}