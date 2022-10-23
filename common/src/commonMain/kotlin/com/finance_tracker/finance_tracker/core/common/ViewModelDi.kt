package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable

@Composable
expect inline fun <reified T : ViewModel> getViewModel(): T