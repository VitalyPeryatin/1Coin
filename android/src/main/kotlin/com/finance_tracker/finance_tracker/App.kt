package com.finance_tracker.finance_tracker

import android.app.Application
import com.finance_tracker.finance_tracker.data.database.DatabaseInitializer
import com.finance_tracker.finance_tracker.di.commonModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

class App: Application(), KoinComponent {

    private val databaseInitializer: DatabaseInitializer by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(commonModules())
        }

        databaseInitializer.init()
    }
}