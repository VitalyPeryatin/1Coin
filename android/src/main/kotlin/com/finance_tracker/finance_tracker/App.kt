package com.finance_tracker.finance_tracker

import android.app.Application
import com.finance_tracker.finance_tracker.data.database.DatabaseInitializer
import com.finance_tracker.finance_tracker.di.commonModules
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

class App: Application(), KoinComponent {

    private val databaseInitializer: DatabaseInitializer by inject()

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Firebase.crashlytics.setCrashlyticsCollectionEnabled(false)
            Napier.base(DebugAntilog())
        } else {
            Firebase.crashlytics.setCrashlyticsCollectionEnabled(true)
            Napier.base(CrashlyticsAntilog())
        }

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(commonModules())
        }

        databaseInitializer.init(this)
    }
}