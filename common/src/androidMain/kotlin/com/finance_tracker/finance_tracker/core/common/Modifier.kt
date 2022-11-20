package com.finance_tracker.finance_tracker.core.common

import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.imePadding as androidImePadding
import androidx.compose.foundation.layout.navigationBarsPadding as androidNavigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding as androidStatusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding as androidSystemBarsPadding

actual fun Modifier.statusBarsPadding(): Modifier = androidStatusBarsPadding()

actual fun Modifier.navigationBarsPadding(): Modifier = androidNavigationBarsPadding()

actual fun Modifier.systemBarsPadding(): Modifier = androidSystemBarsPadding()

actual fun Modifier.imePadding(): Modifier = androidImePadding()

