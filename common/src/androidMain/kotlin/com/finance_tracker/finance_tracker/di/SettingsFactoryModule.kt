package com.finance_tracker.finance_tracker.di

import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.core.module.Module
import org.koin.dsl.module

actual class SettingsFactoryModule {

    actual fun create(): Module = module {
        single<Settings.Factory> {
            SharedPreferencesSettings.Factory(context = get())
        }
    }
}