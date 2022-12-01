package com.finance_tracker.finance_tracker.di

import com.finance_tracker.finance_tracker.data.settings.UserSettings
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val settingsModule = module {
    singleOf(::UserSettings)
}