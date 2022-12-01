package com.finance_tracker.finance_tracker.di

import com.finance_tracker.finance_tracker.core.common.settings.DesktopSettingsFactory
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual class SettingsFactoryModule {

    actual fun create(): Module = module {
        singleOf<Settings.Factory>(::DesktopSettingsFactory)
    }
}