package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.ui.LastTransactionItem
import com.finance_tracker.finance_tracker.core.ui.border
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.finance_tracker.finance_tracker.theme.AppColors
import com.finance_tracker.finance_tracker.theme.CoinTheme
import java.util.*

@Composable
fun LastTransactionWidget(modifier: Modifier = Modifier) {

    val lastTransaction = listOf(
        Transaction(
            id = 0,
            type = TransactionType.Expense,
            amountCurrency = "-$25.52",
            account = Account(
                0,
                Account.Type.DebitCard,
                "Debit card (*5841)",
                CoinTheme.color.content.copy(alpha = 0.5f)
            ),
            amount = 25.52,
            category = Category(
                0,
                "Restaurant",
                1
            ),
            date = Date(28)
        ),
        Transaction(
            id = 1,
            type = TransactionType.Expense,
            amountCurrency = "-$25.52",
            account = Account(
                0,
                Account.Type.DebitCard,
                "Debit card (*5841)",
                CoinTheme.color.content.copy(alpha = 0.5f)
            ),
            amount = 25.52,
            category = Category(
                0,
                "Health",
                2
            ),
            date = Date(28)
        ),
        Transaction(
            id = 2,
            type = TransactionType.Expense,
            amountCurrency = "-$25.52",
            account = Account(
                0,
                Account.Type.DebitCard,
                "Debit card (*5841)",
                CoinTheme.color.content.copy(alpha = 0.5f)
            ),
            amount = 25.52,
            category = Category(
                0,
                "Public Transport",
                8
            ),
            date = Date(28)
        )
    )

    LazyColumn(
        modifier = modifier
            .border(1.dp,CoinTheme.color.secondaryBackground)
            .padding(16.dp)
            .background(CoinTheme.color.background)
            .clip(RoundedCornerShape(12.dp)),
        contentPadding = PaddingValues(start = 12.dp),
    ) {
        items(lastTransaction) { LastTransaction ->
            LastTransactionItem(data = LastTransaction)
        }
    }
}

@Composable
@Preview
fun LastTransactionWidgetPreview() {
    LastTransactionWidget()
}