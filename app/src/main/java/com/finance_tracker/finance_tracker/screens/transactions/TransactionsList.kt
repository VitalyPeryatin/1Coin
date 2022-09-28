package com.finance_tracker.finance_tracker.screens.transactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TransactionsListScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(all = 16.dp)
    ) {
        TopBar()
    }
}

@Composable
@Preview(showBackground = true)
fun TransactionsListScreenPreview() {
    TransactionsListScreen()
}