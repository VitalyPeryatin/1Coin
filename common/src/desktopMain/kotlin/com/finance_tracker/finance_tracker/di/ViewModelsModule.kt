package com.finance_tracker.finance_tracker.di

import com.finance_tracker.finance_tracker.presentation.accounts.AccountsScreenViewModel
import com.finance_tracker.finance_tracker.presentation.add_account.AddAccountViewModel
import com.finance_tracker.finance_tracker.presentation.add_transaction.AddTransactionViewModel
import com.finance_tracker.finance_tracker.presentation.categories.CategorySettingsScreenViewModel
import com.finance_tracker.finance_tracker.presentation.home.HomeScreenViewModel
import com.finance_tracker.finance_tracker.presentation.transactions.TransactionsViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

actual class ViewModelsModule {
    actual fun create(): Module = module {
        factoryOf(::TransactionsViewModel)
        factoryOf(::AccountsScreenViewModel)
        factoryOf(::AddAccountViewModel)
        factoryOf(::HomeScreenViewModel)
        factoryOf(::AddTransactionViewModel)
        factoryOf(::CategorySettingsScreenViewModel)
    }
}