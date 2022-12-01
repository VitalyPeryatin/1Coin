package com.finance_tracker.finance_tracker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.window.singleWindowApplication
import com.finance_tracker.finance_tracker.core.analytics.AnalyticsTracker
import com.finance_tracker.finance_tracker.core.common.EmptyContext
import com.finance_tracker.finance_tracker.core.common.MessageKeyQueue
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import com.finance_tracker.finance_tracker.core.navigation.main.navigationGraph
import com.finance_tracker.finance_tracker.core.navigation.setupNavigation
import com.finance_tracker.finance_tracker.data.database.DatabaseInitializer
import com.finance_tracker.finance_tracker.di.commonModules
import com.finance_tracker.finance_tracker.domain.interactors.UserInteractor
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject

private val databaseInitializer: DatabaseInitializer by inject(DatabaseInitializer::class.java)
private val analyticsTracker: AnalyticsTracker by inject(AnalyticsTracker::class.java)
private val userInteractor: UserInteractor by inject(UserInteractor::class.java)

@OptIn(ExperimentalComposeUiApi::class)
fun main() = singleWindowApplication(
    title = "",
    onKeyEvent = {
        if (
            it.key == Key.DirectionLeft &&
            it.type == KeyEventType.KeyDown
        ) {
            MessageKeyQueue.onBackPressedChannel.trySend(Unit)
            return@singleWindowApplication true
        }
        false
    }
) {
    Napier.base(DebugAntilog())
    initKoin()
    databaseInitializer.init(EmptyContext)
    initAnalytics()

    setupNavigation(MainNavigationTree.Main.name) { navigationGraph() }
}

private fun initKoin() {
    startKoin {
        modules(commonModules())
    }
}

@Composable
private fun initAnalytics() {
    analyticsTracker.init(EmptyContext)
    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        val userId = userInteractor.getOrCreateUserId()
        analyticsTracker.setUserId(userId)
    }
}