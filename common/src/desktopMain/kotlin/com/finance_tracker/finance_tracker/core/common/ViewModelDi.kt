package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable
import org.koin.java.KoinJavaComponent.get

@Composable
actual inline fun <reified T : ViewModel> getViewModel(): T {
    return get(T::class.java)
}