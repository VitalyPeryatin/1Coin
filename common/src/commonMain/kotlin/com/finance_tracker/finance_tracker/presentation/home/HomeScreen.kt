package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.LocalContext
import com.finance_tracker.finance_tracker.core.common.getViewModel
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = getViewModel()
) {

    val accounts by viewModel.accounts.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CoinTheme.color.background)
            .statusBarsPadding()
    ) {

        HomeTopBar()

        MyAccountsHeader(
            modifier = Modifier
                .padding(top = 26.dp)
        )

        AccountsWidget(data = accounts)

    }
}