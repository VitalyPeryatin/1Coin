package com.finance_tracker.finance_tracker.presentation.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.CoinWidget
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypeTab
import com.finance_tracker.finance_tracker.core.ui.tab_rows.TransactionTypesTabRow

@Composable
fun AnalyticsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CoinTheme.color.background)
            .statusBarsPadding()
    ) {
        AnalyticsScreenAppBar()
        var selectedTransactionType by remember { mutableStateOf(TransactionTypeTab.Expense) }
        TransactionTypesTabRow(
            selectedType = selectedTransactionType,
            onSelect = { selectedTransactionType = it }
        )

        CoinWidget(
            title = stringResource(selectedTransactionType.textId) + " " +
                    stringResource("analytics_by_category")
        ) {
            CategoriesPieChart()
        }
    }
}