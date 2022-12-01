package com.finance_tracker.finance_tracker.di

import org.koin.core.module.Module

expect class SettingsFactoryModule() {

    fun create(): Module
}

val SettingsFactoryModule.module: Module
    get() = create()