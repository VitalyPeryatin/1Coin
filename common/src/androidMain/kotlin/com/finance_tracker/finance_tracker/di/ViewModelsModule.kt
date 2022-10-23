package com.finance_tracker.finance_tracker.di

import com.finance_tracker.finance_tracker.presentation.accounts.AccountsScreenViewModel
import com.finance_tracker.finance_tracker.presentation.add_account.AddAccountViewModel
import com.finance_tracker.finance_tracker.presentation.add_transaction.AddTransactionViewModel
import com.finance_tracker.finance_tracker.presentation.categories.CategorySettingsScreenViewModel
import com.finance_tracker.finance_tracker.presentation.home.HomeScreenViewModel
import com.finance_tracker.finance_tracker.presentation.transactions.TransactionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

actual class ViewModelsModule {
    actual fun create(): Module = module {
        viewModelOf(::TransactionsViewModel)
        viewModelOf(::AccountsScreenViewModel)
        viewModelOf(::AddAccountViewModel)
        viewModelOf(::HomeScreenViewModel)
        viewModelOf(::AddTransactionViewModel)
        viewModelOf(::CategorySettingsScreenViewModel)
    }
}