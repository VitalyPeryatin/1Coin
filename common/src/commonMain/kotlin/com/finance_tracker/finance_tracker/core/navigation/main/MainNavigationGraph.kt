package com.finance_tracker.finance_tracker.core.navigation.main

import com.finance_tracker.finance_tracker.core.navigation.tabs.tabsNavigationGraph
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.presentation.add_account.AddAccountScreen
import com.finance_tracker.finance_tracker.presentation.add_category.AddCategoryScreen
import com.finance_tracker.finance_tracker.presentation.add_transaction.AddTransactionScreen
import com.finance_tracker.finance_tracker.presentation.categories.CategorySettingsScreen
import com.finance_tracker.finance_tracker.presentation.detail_account.DetailAccountScreen
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.navigationGraph() {

    screen(MainNavigationTree.AddTransaction.name) {
        AddTransactionScreen(
            transaction = Transaction.EMPTY
        )
    }

    screen(MainNavigationTree.CategorySettings.name) {
        CategorySettingsScreen()
    }

    screen(MainNavigationTree.AddAccount.name) { account ->
        AddAccountScreen(
            account = account as? Account ?: Account.EMPTY
        )
    }

    screen(MainNavigationTree.AddCategory.name) {
        AddCategoryScreen(appBarText = it as String)
    }

    screen(MainNavigationTree.DetailAccount.name) { params ->
        DetailAccountScreen(account = params as Account)
    }

    tabsNavigationGraph()
}