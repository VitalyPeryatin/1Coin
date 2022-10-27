package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable

expect fun getLocalizedString(id: String, context: Context): String

@Composable
fun stringResource(id: String): String {
    return getLocalizedString(
        id = id,
        context = LocalContext.current
    )
}