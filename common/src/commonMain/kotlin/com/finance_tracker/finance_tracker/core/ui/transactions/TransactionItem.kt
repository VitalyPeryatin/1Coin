package com.finance_tracker.finance_tracker.core.ui.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.DecimalFormatType
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionType

@Composable
fun TransactionItem(
    transaction: Transaction,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val category = transaction.category ?: Category.EMPTY
    Row(
        modifier = modifier
            .clickable { onClick.invoke() }
            .padding(vertical = 6.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(44.dp)
                .background(color = CoinTheme.color.secondaryBackground, shape = CircleShape)
                .padding(12.dp),
            painter = rememberVectorPainter(category.iconId),
            contentDescription = null
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .weight(1f)
        ) {
            Text(
                text = category.name,
                style = CoinTheme.typography.body2
            )
            Text(
                text = transaction.account.name,
                style = CoinTheme.typography.subtitle2,
                color = LocalContentColor.current.copy(alpha = 0.5f)
            )
        }

        val sign = if (transaction.type == TransactionType.Income) "+" else "-"
        Text(
            text = sign + transaction.amountCurrency + DecimalFormatType.Amount.format(transaction.amount),
            style = CoinTheme.typography.body2,
            color = if (transaction.type == TransactionType.Income) {
                CoinTheme.color.accentGreen
            } else {
                CoinTheme.color.content
            }
        )
    }
}