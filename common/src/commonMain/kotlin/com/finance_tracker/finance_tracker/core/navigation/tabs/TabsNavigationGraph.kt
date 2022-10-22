package com.finance_tracker.finance_tracker.core.navigation.tabs

import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import com.finance_tracker.finance_tracker.presentation.accounts.AccountsScreen
import com.finance_tracker.finance_tracker.presentation.analytics.AnalyticsScreen
import com.finance_tracker.finance_tracker.presentation.home.HomeScreen
import com.finance_tracker.finance_tracker.presentation.tabs_navigation.CustomConfiguration
import com.finance_tracker.finance_tracker.presentation.tabs_navigation.TabsNavigationScreen
import com.finance_tracker.finance_tracker.presentation.tabs_navigation.tabs.AccountsTab
import com.finance_tracker.finance_tracker.presentation.tabs_navigation.tabs.AnalyticsTab
import com.finance_tracker.finance_tracker.presentation.tabs_navigation.tabs.HomeTab
import com.finance_tracker.finance_tracker.presentation.tabs_navigation.tabs.TransactionsTab
import com.finance_tracker.finance_tracker.presentation.transactions.TransactionsScreen
import ru.alexgladkov.odyssey.compose.extensions.customNavigation
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.extensions.tab
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.tabsNavigationGraph() {
    customNavigation(
        name = MainNavigationTree.Main.name,
        tabsNavModel = CustomConfiguration(
            content = { TabsNavigationScreen() }
        )
    ) {
        tab(HomeTab()) {
            screen(name = TabsNavigationTree.Home.name) {
                HomeScreen()
            }
        }
        tab(TransactionsTab()) {
            screen(name = TabsNavigationTree.Transactions.name) {
                TransactionsScreen()
            }
        }
        tab(AccountsTab()) {
            screen(name = TabsNavigationTree.Accounts.name) {
                AccountsScreen()
            }
        }
        tab(AnalyticsTab()) {
            screen(name = TabsNavigationTree.Analytics.name) {
                AnalyticsScreen()
            }
        }
    }
}