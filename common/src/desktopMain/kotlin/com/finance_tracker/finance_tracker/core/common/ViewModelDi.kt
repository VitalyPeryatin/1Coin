package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.koin.core.context.GlobalContext

@Composable
actual inline fun <reified T : ViewModel> getViewModel(): T {
    val viewModel by remember {
        mutableStateOf<T>(GlobalContext.get().get())
    }
    return viewModel
}