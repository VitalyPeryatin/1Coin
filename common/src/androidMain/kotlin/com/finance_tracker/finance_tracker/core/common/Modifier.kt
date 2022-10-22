package com.finance_tracker.finance_tracker.core.common

import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.navigationBarsPadding as androidNavigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding as androidStatusBarsPadding

actual fun Modifier.statusBarsPadding(): Modifier = androidStatusBarsPadding()

actual fun Modifier.navigationBarsPadding(): Modifier = androidNavigationBarsPadding()