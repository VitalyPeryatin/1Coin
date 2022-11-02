package com.finance_tracker.finance_tracker.core.common

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val animateScrollValue = 1.3f

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T : Any> LazyDragColumn(
    items: List<T>,
    onSwap: (Int, Int) -> Unit,
    contentPaddingValues: PaddingValues,
    itemContent: @Composable (LazyItemScope.(index : Int, item: T) -> Unit),
) {
    var overscrollJob by remember { mutableStateOf<Job?>(null) }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val dragDropState = rememberDragDropState(listState) { fromIndex, toIndex ->
        onSwap(fromIndex, toIndex)
    }

    LazyColumn(
        modifier = Modifier
            .pointerInput(dragDropState) {
                detectDragGesturesAfterLongPress(
                    onDrag = { change, offset ->

                        change.consume()
                        dragDropState.onDrag(offset = offset)

                        if (overscrollJob?.isActive == true) {
                            return@detectDragGesturesAfterLongPress
                        }

                        dragDropState
                            .checkForOverScroll()
                            .takeIf { it != 0f }
                            ?.let {
                                overscrollJob = scope.launch {
                                    dragDropState.state.animateScrollBy(
                                        it * animateScrollValue, tween(easing = FastOutLinearInEasing)
                                        )
                                    }
                            } ?: run { overscrollJob?.cancel() }
                    },
                    onDragStart = { offset -> dragDropState.onDragStart(offset) },
                    onDragEnd = {
                        dragDropState.onDragInterrupted()
                        overscrollJob?.cancel()
                    },
                    onDragCancel = {
                        dragDropState.onDragInterrupted()
                        overscrollJob?.cancel()
                    }
                )
            },
        state = listState,
        contentPadding = contentPaddingValues
    ) {
        itemsIndexed(items = items) { index, item ->
            DraggableItem(
                dragDropState = dragDropState,
                index = index
            ) { isDragging ->
                val elevation by animateDpAsState(if (isDragging) 4.dp else 0.dp)
                Card(
                    elevation = elevation,
                    shape = when (index) {
                        0 -> {
                            RoundedCornerShape(
                                topStart = 12.dp,
                                topEnd = 12.dp,
                            )
                        }
                        items.lastIndex -> {
                            RoundedCornerShape(
                                bottomStart = 12.dp,
                                bottomEnd = 12.dp
                            )
                        }
                        else -> {
                            RoundedCornerShape(0.dp)
                        }
                    },
                ) {
                    itemContent(index, item)
                }
            }
        }
    }
}