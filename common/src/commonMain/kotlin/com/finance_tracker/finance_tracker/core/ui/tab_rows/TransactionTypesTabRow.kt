package com.finance_tracker.finance_tracker.core.ui.tab_rows

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.core.common.stringResource

enum class TransactionTypeTab(val textId: String) {
    Expense("tab_expense"),
    Income("tab_income"),
}

@Composable
fun TransactionTypesTabRow(
    modifier: Modifier = Modifier,
    selectedType: TransactionTypeTab = TransactionTypeTab.Expense,
    onSelect: (TransactionTypeTab) -> Unit = {}
) {
    val tabs = TransactionTypeTab.values()
    val tabToIndexMap = tabs
        .mapIndexed { index, tab -> tab to index }
        .toMap()

    CoinTabRow(
        data = tabs.map { stringResource(it.textId) },
        modifier = modifier,
        selectedTabIndex = tabToIndexMap.getValue(selectedType),
        onTabSelect = { index -> onSelect.invoke(tabs[index]) }
    )
}