package com.finance_tracker.finance_tracker.core.theme

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.extensions.setupWithActivity
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalNavigator

fun ComponentActivity.setupThemedNavigation(
    startScreen: String,
    vararg providers: ProvidedValue<*>,
    navigationGraph: RootComposeBuilder.() -> Unit
) {
    setContent {
        CoinTheme {
            val rootController = RootComposeBuilder()
                .apply(navigationGraph).build()
            rootController.backgroundColor = CoinTheme.color.background
            rootController.setupWithActivity(this)

            CompositionLocalProvider(
                *providers,
                LocalRootController provides rootController
            ) {
                ModalNavigator {
                    Navigator(startScreen)
                }
            }
        }
    }
}