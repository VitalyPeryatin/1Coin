package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.LastTransactionItem
import com.finance_tracker.finance_tracker.core.ui.border
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import java.util.*

@Composable
fun LastTransactionWidget(modifier: Modifier = Modifier) {

    val lastTransaction = listOf(
        Transaction(
            id = 0,
            type = TransactionType.Expense,
            amountCurrency = "-$25.52",
            account = Account(
                id = 0,
                type = Account.Type.DebitCard,
                name = "Debit card (*5841)",
                balance = 100.0,
                color = CoinTheme.color.content.copy(alpha = 0.5f)
            ),
            amount = 25.52,
            category = Category.EMPTY,
            date = Date(28)
        ),
        Transaction(
            id = 1,
            type = TransactionType.Expense,
            amountCurrency = "-$25.52",
            account = Account(
                id = 0,
                type = Account.Type.DebitCard,
                name = "Debit card (*5841)",
                balance = 100.0,
                color = CoinTheme.color.content.copy(alpha = 0.5f)
            ),
            amount = 25.52,
            category = Category.EMPTY,
            date = Date(28)
        ),
        Transaction(
            id = 2,
            type = TransactionType.Expense,
            amountCurrency = "-$25.52",
            account = Account(
                id = 0,
                type = Account.Type.DebitCard,
                name = "Debit card (*5841)",
                balance = 100.0,
                color = CoinTheme.color.content.copy(alpha = 0.5f)
            ),
            amount = 25.52,
            category = Category.EMPTY,
            date = Date(28)
        )
    )

    LazyColumn(
        modifier = modifier
            .padding(16.dp)
            .border(
                1.dp,
                CoinTheme.color.content.copy(0.4f),
                radius = 12.dp
            )
            .clip(RoundedCornerShape(12.dp)),
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 4.dp
        )
    ) {
        items(lastTransaction) { LastTransaction ->
            LastTransactionItem(data = LastTransaction)
        }
    }
}