package com.finance_tracker.finance_tracker.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalNavigator

@Composable
fun setupThemedNavigation(
    startScreen: String,
    vararg providers: ProvidedValue<*>,
    navigationGraph: RootComposeBuilder.() -> Unit
) {
    CoinTheme {
        val rootController = RootComposeBuilder()
            .apply(navigationGraph).build()
        rootController.backgroundColor = CoinTheme.color.background

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