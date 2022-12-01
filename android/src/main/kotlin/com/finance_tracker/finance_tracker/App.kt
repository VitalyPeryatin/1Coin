package com.finance_tracker.finance_tracker

import android.app.Application
import com.finance_tracker.finance_tracker.core.analytics.AnalyticsTracker
import com.finance_tracker.finance_tracker.data.database.DatabaseInitializer
import com.finance_tracker.finance_tracker.di.commonModules
import com.finance_tracker.finance_tracker.domain.interactors.UserInteractor
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import kotlin.coroutines.CoroutineContext

class App : Application(), KoinComponent, CoroutineScope {

    private val databaseInitializer: DatabaseInitializer by inject()
    private val analyticsTracker: AnalyticsTracker by inject()
    private val userInteractor: UserInteractor by inject()
    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main.immediate +
            CoroutineExceptionHandler { _, throwable ->
                Napier.e("App", throwable)
            }

    override fun onCreate() {
        super.onCreate()

        initLogger()
        initDi()
        initAnalytics()
        initDatabase()
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Firebase.crashlytics.setCrashlyticsCollectionEnabled(false)
            Napier.base(DebugAntilog())
        } else {
            Firebase.crashlytics.setCrashlyticsCollectionEnabled(true)
            Napier.base(CrashlyticsAntilog())
        }
    }

    private fun initDi() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(commonModules())
        }
    }

    private fun initAnalytics() {
        analyticsTracker.init(this@App)
        launch {
            val userId = userInteractor.getOrCreateUserId()
            analyticsTracker.setUserId(userId)
        }
    }

    private fun initDatabase() {
        databaseInitializer.init(this)
    }
}