package com.finance_tracker.finance_tracker.di

fun commonModules() = listOf(
    coreModule,
    DriverFactoryModule().module,
    viewModelsModule,
    databaseModule,
    networkModule,
    repositoriesModule,
    domainModule
)