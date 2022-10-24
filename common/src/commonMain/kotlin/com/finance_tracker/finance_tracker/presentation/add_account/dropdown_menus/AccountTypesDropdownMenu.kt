package com.finance_tracker.finance_tracker.presentation.add_account.dropdown_menus

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.CoinDropdownMenu
import com.finance_tracker.finance_tracker.core.ui.DropdownMenuItem

@Composable
fun AccountTypesDropdownMenu(
    items: List<String>,
    expandedState: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    onSelect: (String) -> Unit = {}
) {
    CoinDropdownMenu(
        modifier = modifier,
        expanded = expandedState.value,
        onDismissRequest = { expandedState.value = false }
    ) {
        items.forEach { item ->
            DropdownMenuItem(
                onClick = {
                    onSelect.invoke(item)
                    expandedState.value = false
                }
            ) {
                Text(
                    text = item,
                    style = CoinTheme.typography.body1
                )
            }
        }
    }
}