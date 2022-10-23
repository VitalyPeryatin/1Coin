package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.DecimalFormatType
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.Account

@Composable
fun AccountCard(
    data: Account,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .size(
                width = 160.dp,
                height = 128.dp
            ),
        backgroundColor = data.color,
        shape = RoundedCornerShape(12.dp),
    ) {
        Column {
            Icon(
                imageVector = loadXmlPicture("ic_wallet_active"),
                contentDescription = null,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp
                    )
                    .size(29.dp),
                tint = CoinTheme.color.primaryVariant
            )
            Text(
                text = DecimalFormatType.Amount.format(data.balance),
                style = CoinTheme.typography.h5,
                color = CoinTheme.color.primaryVariant,
                modifier = Modifier
                    .padding(
                        top = 30.dp,
                        start = 16.dp
                    )
            )
            Text(
                text = data.name,
                style = CoinTheme.typography.subtitle2,
                color = CoinTheme.color.primaryVariant.copy(alpha = 0.5f),
                modifier = Modifier
                    .padding(start = 16.dp)
            )
        }
    }
}