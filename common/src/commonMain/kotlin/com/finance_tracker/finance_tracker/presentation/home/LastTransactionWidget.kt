package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.TransactionItem
import com.finance_tracker.finance_tracker.core.ui.border
import com.finance_tracker.finance_tracker.core.ui.transactions.CommonTransactionsList
import com.finance_tracker.finance_tracker.domain.models.*
import com.finance_tracker.finance_tracker.domain.models.Currency
import java.util.*

@Composable
fun LastTransactionWidget(
    transactions: List<Transaction>,
    modifier: Modifier = Modifier
) {
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
                color = CoinTheme.color.content.copy(alpha = 0.5f),
                currency = Currency("dollar","$")
            ),
            amount = 25.52,
            category = Category(
                id = 0,
                name = "Restaurant",
                iconId = "ic_category_1"
            ),
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
                color = CoinTheme.color.content.copy(alpha = 0.5f),
                currency = Currency("dollar","$")
            ),
            amount = 25.52,
            category = Category(
                id = 1,
                name = "Health",
                iconId = "ic_category_2"
            ),
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
                color = CoinTheme.color.content.copy(alpha = 0.5f),
                currency = Currency("dollar","$")
            ),
            amount = 25.52,
            category = Category(
                id = 2,
                name = "Public Transport",
                iconId = "ic_category_8"
            ),
            date = Date(28)
        )
    )
CommonTransactionList(lastTransaction)
//    LazyColumn(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//            .border(
//                strokeWidth = 1.dp,
//                color = CoinTheme.color.content.copy(0.4f),
//                radius = 12.dp
//            ),
//        contentPadding = PaddingValues(
//            top = 16.dp,
//            bottom = 4.dp
//        )
//    ) {
//        items(CommonTransactionsList()) { LastTransaction ->
//            TransactionItem(transaction = lastTransaction)
//        }
//    }
}