package com.finance_tracker.finance_tracker.di

import org.koin.core.module.Module

expect class ViewModelsModule() {
    fun create(): Module
}

val ViewModelsModule.module: Module
    get() = create()