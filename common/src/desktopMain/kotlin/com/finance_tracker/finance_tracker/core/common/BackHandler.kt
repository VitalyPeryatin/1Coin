package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

object MessageKeyQueue {
    val onBackPressedChannel = Channel<Unit>(capacity = Int.MAX_VALUE, onBufferOverflow = BufferOverflow.DROP_OLDEST)
}

@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
    LaunchedEffect(Unit) {
        MessageKeyQueue.onBackPressedChannel
            .receiveAsFlow()
            .onEach {
                if (enabled) {
                    onBack.invoke()
                }
            }
            .launchIn(this)
    }
}