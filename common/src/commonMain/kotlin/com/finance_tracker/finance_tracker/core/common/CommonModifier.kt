package com.finance_tracker.finance_tracker.core.common

import androidx.compose.ui.Modifier

fun Modifier.`if`(
    condition: Boolean,
    then: Modifier.() -> Modifier
): Modifier {
    return if (condition) {
        then()
    } else {
        this
    }
}

expect fun Modifier.statusBarsPadding(): Modifier

expect fun Modifier.navigationBarsPadding(): Modifier

expect fun Modifier.systemBarsPadding(): Modifier