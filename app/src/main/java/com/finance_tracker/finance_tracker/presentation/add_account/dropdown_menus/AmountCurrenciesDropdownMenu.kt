package com.finance_tracker.finance_tracker.presentation.add_account.dropdown_menus

import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.finance_tracker.finance_tracker.core.ui.CoinDropdownMenu
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.theme.CoinTheme

@Composable
fun AmountCurrenciesDropdownMenu(
    items: List<Currency>,
    expandedState: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    onCurrencySelect: (Currency) -> Unit = {}
) {
    CoinDropdownMenu(
        modifier = modifier,
        expanded = expandedState.value,
        onDismissRequest = { expandedState.value = false }
    ) {
        items.forEach { item ->
            DropdownMenuItem(
                onClick = {
                    onCurrencySelect.invoke(item)
                    expandedState.value = false
                }
            ) {
                Text(
                    text = "${item.name} (${item.sign})",
                    style = CoinTheme.typography.body1
                )
            }
        }
    }
}

@Preview
@Composable
fun AmountCurrenciesDropdownMenuPreview() {
    AmountCurrenciesDropdownMenu(
        items = listOf(Currency(name = "USD", sign = "\$")),
        expandedState = mutableStateOf(false)
    )
}