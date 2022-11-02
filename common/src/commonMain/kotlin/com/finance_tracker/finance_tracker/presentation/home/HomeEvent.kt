package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.lazy.LazyListState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

sealed interface HomeEvent {
    data class ScrollToItemAccounts(val index: Int): HomeEvent
}

fun handleEvent(
    event: HomeEvent,
    coroutineScope: CoroutineScope,
    accountsLazyListState: LazyListState
) {
    when (event) {
        is HomeEvent.ScrollToItemAccounts -> {
            coroutineScope.launch {
                accountsLazyListState.animateScrollToItem(event.index)
            }
        }
    }
}