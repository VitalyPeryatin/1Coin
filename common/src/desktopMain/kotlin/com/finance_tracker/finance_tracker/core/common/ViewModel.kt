package com.finance_tracker.finance_tracker.core.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

actual abstract class ViewModel {
    actual val viewModelScope: CoroutineScope = MainScope()

    protected actual open fun onCleared() {
    }
}