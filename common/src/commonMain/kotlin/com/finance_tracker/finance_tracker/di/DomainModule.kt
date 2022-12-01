package com.finance_tracker.finance_tracker.di

import com.finance_tracker.finance_tracker.domain.interactors.CurrencyConverterInteractor
import com.finance_tracker.finance_tracker.domain.interactors.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.interactors.UserInteractor
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val domainModule = module {
    factoryOf(::TransactionsInteractor)
    factoryOf(::CurrencyConverterInteractor)
    factoryOf(::UserInteractor)
}