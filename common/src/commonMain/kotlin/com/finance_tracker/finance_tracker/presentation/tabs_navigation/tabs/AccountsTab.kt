package com.finance_tracker.finance_tracker.presentation.tabs_navigation.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.finance_tracker.finance_tracker.core.common.LocalContext
import com.finance_tracker.finance_tracker.core.common.getLocalizedString
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.loadXmlPicture
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabConfiguration
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabItem

class AccountsTab : TabItem() {

    override val configuration: TabConfiguration
        @Composable
        get() {
            val context = LocalContext.current
            return TabConfiguration(
                title = getLocalizedString("tab_accounts", context),
                selectedIcon = rememberVectorPainter(loadXmlPicture("ic_wallet_active")),
                unselectedIcon = rememberVectorPainter(loadXmlPicture("ic_wallet_inactive")),
                selectedColor = CoinTheme.color.primary,
                unselectedColor = CoinTheme.color.content
            )
        }
}