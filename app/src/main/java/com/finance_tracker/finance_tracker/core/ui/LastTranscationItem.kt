package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.R
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.finance_tracker.finance_tracker.theme.CoinTheme
import java.util.*

@Composable
fun LastTransactionItem(
    data: Transaction,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            bottom = 12.dp
        )
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_category_1),
            contentDescription = null,
            modifier = modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(
                    CoinTheme.color.secondaryBackground,
                    CircleShape
                )
                .padding(10.dp),
            tint = CoinTheme.color.content
        )
        Column() {
            data.category?.let {
                Text(
                    text = it.name,
                    style = CoinTheme.typography.body2,
                    color = CoinTheme.color.content,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
            Spacer(modifier = modifier.padding(horizontal = 2.dp))
            Text(
                text = data.account.name,
                style = CoinTheme.typography.subtitle2,
                color = CoinTheme.color.content.copy(alpha = 0.5f),
                modifier = Modifier.padding(start = 12.dp)
            )
        }
        Spacer(modifier = Modifier.padding(start = 87.dp))
        Text(
                text = data.amountCurrency,
                style = CoinTheme.typography.body2,
                color = CoinTheme.color.content,
            )
    }
}

@Composable
@Preview
fun LastTransactionItemPreview() {

    // TODO - ПРЕВЬЮ ДАННЫЕ
    val testLastTransaction = Transaction(
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
    )

    LastTransactionItem(data = testLastTransaction)
}