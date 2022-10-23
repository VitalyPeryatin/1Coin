package com.finance_tracker.finance_tracker.presentation.add_transaction.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.LocalContext
import com.finance_tracker.finance_tracker.core.common.getLocalizedString
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

@Composable
fun AmountTextField(
    currency: String,
    modifier: Modifier = Modifier,
    amount: Double = 0.0
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .background(Color.White)
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$currency$amount",
            color = Color.Black.copy(alpha = 0.8f),
            style = CoinTheme.typography.h1
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = getLocalizedString("add_transaction_amount", context),
            color = Color.Black.copy(alpha = 0.3f),
            style = CoinTheme.typography.subtitle2
        )
    }
}