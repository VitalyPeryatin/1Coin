package com.finance_tracker.finance_tracker.core.ui.transactions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.DecimalFormatType
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import com.finance_tracker.finance_tracker.domain.models.TransactionType

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionItem(
    transactionData: TransactionListModel.Data,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
) {
    val transaction = transactionData.transaction
    val category = transaction.category ?: Category.EMPTY
    val isSelected by transactionData.isSelected
    Row(
        modifier = modifier
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
            )
            .background(
                color = if (isSelected) {
                    CoinTheme.color.content.copy(alpha = 0.1f)
                } else {
                    Color.Transparent
                }
            )
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
            text = sign + transaction.amountCurrency.sign + DecimalFormatType.Amount.format(transaction.amount),
            style = CoinTheme.typography.body2,
            color = if (transaction.type == TransactionType.Income) {
                CoinTheme.color.accentGreen
            } else {
                CoinTheme.color.content
            }
        )
    }
}