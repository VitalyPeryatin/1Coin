package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.getViewModel as koinGetViewModel

@Composable
actual inline fun <reified T : ViewModel> getViewModel(): T {
    return koinGetViewModel()
}