package com.finance_tracker.finance_tracker.core.navigation.main

import com.finance_tracker.finance_tracker.core.navigation.tabs.tabsNavigationGraph
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.presentation.add_account.AddAccountScreen
import com.finance_tracker.finance_tracker.presentation.add_category.AddCategoryScreen
import com.finance_tracker.finance_tracker.presentation.add_category.AddCategoryScreenParams
import com.finance_tracker.finance_tracker.presentation.add_transaction.AddTransactionScreen
import com.finance_tracker.finance_tracker.presentation.category_settings.CategorySettingsScreen
import com.finance_tracker.finance_tracker.presentation.detail_account.DetailAccountScreen
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.navigationGraph() {

    screen(MainNavigationTree.AddTransaction.name) {
        CoinTheme {
            AddTransactionScreen(
                transaction = it as? Transaction
            )
        }
    }

    screen(MainNavigationTree.CategorySettings.name) {
        CoinTheme {
            CategorySettingsScreen()
        }
    }

    screen(MainNavigationTree.AddAccount.name) { account ->
        CoinTheme {
            AddAccountScreen(
                account = account as? Account ?: Account.EMPTY
            )
        }
    }

    screen(MainNavigationTree.AddCategory.name) { params ->
        CoinTheme {
            AddCategoryScreen(
                addCategoryScreenParams = params as AddCategoryScreenParams
            )
        }
    }

    screen(MainNavigationTree.DetailAccount.name) { params ->
        CoinTheme {
            DetailAccountScreen(account = params as Account)
        }
    }

    tabsNavigationGraph()
}