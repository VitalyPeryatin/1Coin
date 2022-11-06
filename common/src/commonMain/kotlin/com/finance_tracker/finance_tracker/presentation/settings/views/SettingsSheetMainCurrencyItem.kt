package com.finance_tracker.finance_tracker.presentation.settings.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.presentation.add_account.views.CurrencySelector

@Composable
fun SettingsSheetMainCurrencyItem(
    selectedCurrency: Currency,
    modifier: Modifier = Modifier,
    onCurrencySelect: (Currency) -> Unit = {},
) {
    Row(
        modifier = modifier
            .padding(
                vertical = 12.dp,
                horizontal = 16.dp
            )
            .fillMaxWidth()
    ) {
        Icon(
            painter = rememberVectorPainter(id = "ic_dollar"),
            contentDescription = null,
            modifier = Modifier
                .padding(
                    end = 8.dp
                )
                .size(24.dp)
                .align(Alignment.CenterVertically),
            tint = CoinTheme.color.content.copy(alpha = 0.6f)
        )
        Text(
            text = stringResource("settings_main_currency"),
            modifier = Modifier
                .padding(end = 8.dp)
                .align(Alignment.CenterVertically),
            style = CoinTheme.typography.body1
        )
        Spacer(Modifier.weight(1f))
        CurrencySelector(
            items = Currency.list,
            selectedCurrency = selectedCurrency,
            onCurrencySelect = onCurrencySelect,
            modifier = Modifier
                .align(Alignment.CenterVertically),
            xOffset = 240.dp,
            yOffset = 12.dp
        )
    }
}