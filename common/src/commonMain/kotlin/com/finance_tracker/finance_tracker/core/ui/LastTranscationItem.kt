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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.core.ui.

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
            painter = rememberVectorPainter("ic_category_1"),
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
        Column {
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