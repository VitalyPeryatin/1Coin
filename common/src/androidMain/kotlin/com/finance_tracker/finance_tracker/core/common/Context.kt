package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import android.content.Context as AndroidContext

actual typealias Context = AndroidContext

@Composable
actual fun getContext(): Context = LocalContext.current