package com.finance_tracker.finance_tracker.di

fun commonModules() = listOf(
    coreModule,
    DriverFactoryModule().module,
    AnalyticsFactoryModule().module,
    SettingsFactoryModule().module,
    analyticsModule,
    viewModelsModule,
    databaseModule,
    settingsModule,
    networkModule,
    repositoriesModule,
    domainModule,
    sourcesModule,
)