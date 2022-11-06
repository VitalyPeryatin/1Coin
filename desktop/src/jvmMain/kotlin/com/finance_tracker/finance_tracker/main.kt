package com.finance_tracker.finance_tracker

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.window.singleWindowApplication
import com.finance_tracker.finance_tracker.core.common.MessageKeyQueue
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import com.finance_tracker.finance_tracker.core.navigation.main.navigationGraph
import com.finance_tracker.finance_tracker.core.navigation.setupNavigation
import com.finance_tracker.finance_tracker.data.database.DatabaseInitializer
import com.finance_tracker.finance_tracker.di.commonModules
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject

private val databaseInitializer: DatabaseInitializer by inject(DatabaseInitializer::class.java)

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
    initKoin()
    databaseInitializer.init()
    Napier.base(DebugAntilog())
    setupNavigation(MainNavigationTree.Main.name) { navigationGraph() }
}

private fun initKoin() {
    startKoin {
        modules(commonModules())
    }
}