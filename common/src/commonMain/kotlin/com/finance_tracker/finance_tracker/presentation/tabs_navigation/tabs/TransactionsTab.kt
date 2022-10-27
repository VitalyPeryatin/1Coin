package com.finance_tracker.finance_tracker.presentation.tabs_navigation.tabs

import androidx.compose.runtime.Composable
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabConfiguration
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabItem

class TransactionsTab : TabItem() {

    override val configuration: TabConfiguration
        @Composable
        get() {
            return TabConfiguration(
                title = stringResource("tab_transactions"),
                selectedIcon = rememberVectorPainter("ic_transactions_active"),
                unselectedIcon = rememberVectorPainter("ic_transactions_inactive"),
                selectedColor = CoinTheme.color.primary,
                unselectedColor = CoinTheme.color.content,
            )
        }
}