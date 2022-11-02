package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable
import androidx.activity.compose.BackHandler as AndroidBackHandler

@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
    return AndroidBackHandler(enabled, onBack)
}