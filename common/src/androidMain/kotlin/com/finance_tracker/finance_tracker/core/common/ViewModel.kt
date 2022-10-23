package com.finance_tracker.finance_tracker.core.common

import kotlinx.coroutines.CoroutineScope
import androidx.lifecycle.ViewModel as AndroidViewModel
import androidx.lifecycle.viewModelScope as androidViewModelScope

actual abstract class ViewModel: AndroidViewModel() {
    actual val viewModelScope: CoroutineScope = androidViewModelScope

    actual override fun onCleared() {
        super.onCleared()
    }

}