package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

expect abstract class Context()

@Composable
expect fun getContext(): Context

val LocalContext = staticCompositionLocalOf<Context> {
    error("CompositionLocal LocalContext not present")
}