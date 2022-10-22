package com.finance_tracker.finance_tracker.presentation.accounts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.getViewModel
import com.finance_tracker.finance_tracker.core.ui.AccountCard

@Composable
fun AccountsScreen(
    viewModel: AccountsScreenViewModel = getViewModel(),
) {

    Column {

        AccountsAppBar()

        val accounts by viewModel.accounts.collectAsState()

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxHeight(),
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            items(accounts) { account ->
                AccountCard(data = account)
            }
        }
    }

}