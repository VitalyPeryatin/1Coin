package com.finance_tracker.finance_tracker

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import com.finance_tracker.finance_tracker.core.navigation.main.navigationGraph
import com.finance_tracker.finance_tracker.core.theme.setupThemedNavigation
import com.finance_tracker.finance_tracker.di.commonModules
import org.koin.core.context.startKoin


fun main() = application {
    initKoin()
    Window(onCloseRequest = ::exitApplication) {
        setupThemedNavigation(MainNavigationTree.Main.name) { navigationGraph() }
    }
}

private fun initKoin() {
    startKoin {
        modules(commonModules())
    }
}