package com.finance_tracker.finance_tracker.core.common

import androidx.compose.ui.res.useResource

actual fun getRaw(context: Context, name: String, ext: String): String {
    return useResource("raw/$name.$ext") {
        it.bufferedReader().readText()
    }
}