package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable
import org.koin.core.context.GlobalContext

@Composable
actual inline fun <reified T : ViewModel> getViewModel(): T {
    return GlobalContext.get().get()
}