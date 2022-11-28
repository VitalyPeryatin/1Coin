package com.finance_tracker.finance_tracker.di

import com.finance_tracker.finance_tracker.data.data_sources.TransactionSource
import com.finance_tracker.finance_tracker.data.data_sources.TransactionSourceFactory
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val sourcesModule = module {
    factoryOf(::TransactionSource)
    factoryOf(::TransactionSourceFactory)
}