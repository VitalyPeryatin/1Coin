package com.finance_tracker.finance_tracker.core.navigation.main

import com.finance_tracker.finance_tracker.core.navigation.tabs.tabsNavigationGraph
import com.finance_tracker.finance_tracker.presentation.add_transaction.AddTransactionScreen
import com.finance_tracker.finance_tracker.presentation.categories.CategorySettingsScreen
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.navigationGraph() {
    screen(MainNavigationTree.AddTransaction.name) {
        AddTransactionScreen()
    }

    screen(MainNavigationTree.CategorySettings.name) {
        CategorySettingsScreen()
    }

    tabsNavigationGraph()
}