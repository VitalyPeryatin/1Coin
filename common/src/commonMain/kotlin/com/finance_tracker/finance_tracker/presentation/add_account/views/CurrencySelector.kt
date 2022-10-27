package com.finance_tracker.finance_tracker.presentation.add_account.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.presentation.add_account.dropdown_menus.AmountCurrenciesDropdownMenu

@Composable
fun CurrencySelector(
    items: List<Currency>,
    selectedCurrency: Currency,
    modifier: Modifier = Modifier,
    onCurrencySelect: (Currency) -> Unit = {}
) {
    val currencyMenuExpanded = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier
            .padding(end = 12.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = CoinTheme.color.secondaryBackground,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                focusManager.clearFocus()
                currencyMenuExpanded.value = true
            }
            .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = selectedCurrency.sign,
            color = CoinTheme.color.content,
            style = CoinTheme.typography.body2_medium
        )
        Icon(
            modifier = Modifier
                .size(24.dp)
                .padding(start = 8.dp),
            painter = rememberVectorPainter("ic_expand_more"),
            contentDescription = null,
            tint = CoinTheme.color.content
        )
    }

    AmountCurrenciesDropdownMenu(
        items = items,
        expandedState = currencyMenuExpanded,
        onCurrencySelect = onCurrencySelect
    )
}