package com.finance_tracker.finance_tracker.di

import com.finance_tracker.finance_tracker.data.network.CurrenciesNetworkDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val networkModule = module {
    singleOf(::CurrenciesNetworkDataSource)
}