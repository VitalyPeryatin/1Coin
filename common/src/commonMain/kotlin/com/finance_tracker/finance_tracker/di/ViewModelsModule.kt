package com.finance_tracker.finance_tracker.di

import com.finance_tracker.finance_tracker.presentation.accounts.AccountsViewModel
import com.finance_tracker.finance_tracker.presentation.add_account.AddAccountViewModel
import com.finance_tracker.finance_tracker.presentation.add_category.AddCategoryViewModel
import com.finance_tracker.finance_tracker.presentation.add_transaction.AddTransactionViewModel
import com.finance_tracker.finance_tracker.presentation.categories.CategorySettingsViewModel
import com.finance_tracker.finance_tracker.presentation.home.HomeViewModel
import com.finance_tracker.finance_tracker.presentation.transactions.TransactionsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val viewModelsModule = module {
    factoryOf(::TransactionsViewModel)
    factoryOf(::AccountsViewModel)
    factoryOf(::AddAccountViewModel)
    factoryOf(::HomeViewModel)
    factoryOf(::AddTransactionViewModel)
    factoryOf(::CategorySettingsViewModel)
    factoryOf(::AddCategoryViewModel)
}