package com.finance_tracker.finance_tracker.di

import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.data.repositories.CategoriesRepository
import com.finance_tracker.finance_tracker.data.repositories.CurrenciesRepository
import com.finance_tracker.finance_tracker.data.repositories.TransactionsRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val repositoriesModule = module {
    factoryOf(::AccountsRepository)
    factoryOf(::TransactionsRepository)
    factoryOf(::CategoriesRepository)
    factoryOf(::CurrenciesRepository)
}