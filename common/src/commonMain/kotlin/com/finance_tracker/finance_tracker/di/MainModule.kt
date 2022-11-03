package com.finance_tracker.finance_tracker.di

fun commonModules() = listOf(
    DriverFactoryModule().module,
    viewModelsModule,
    databaseModule,
    repositoriesModule,
    domainModule
)