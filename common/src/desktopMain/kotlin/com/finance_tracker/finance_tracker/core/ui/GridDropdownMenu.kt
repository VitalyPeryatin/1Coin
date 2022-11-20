package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.awtEventOrNull
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.input.InputMode
import androidx.compose.ui.input.InputModeManager
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalInputModeManager
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import java.awt.event.KeyEvent

private val androidx.compose.ui.input.key.KeyEvent.isDirectionDown: Boolean
    get() = key.nativeKeyCode == KeyEvent.VK_DOWN
private val androidx.compose.ui.input.key.KeyEvent.isDirectionUp: Boolean
    get() = key.nativeKeyCode == KeyEvent.VK_UP
private val androidx.compose.ui.input.key.KeyEvent.isDirectionLeft: Boolean
    get() = key.nativeKeyCode == KeyEvent.VK_LEFT
private val androidx.compose.ui.input.key.KeyEvent.isDirectionRight: Boolean
    get() = key.nativeKeyCode == KeyEvent.VK_RIGHT
private val MenuVerticalMargin = 48.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GridDropdownMenu(
    columnSize: Dp,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    focusable: Boolean = true,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    content: LazyGridScope.() -> Unit = {}
) {
    val expandedStates = remember { MutableTransitionState(false) }
    expandedStates.targetState = expanded

    if (expandedStates.currentState || expandedStates.targetState) {
        val transformOriginState = remember { mutableStateOf(TransformOrigin.Center) }
        val density = LocalDensity.current
        // The original [DropdownMenuPositionProvider] is not yet suitable for large screen devices,
        // so we need to make additional checks and adjust the position of the [DropdownMenu] to
        // avoid content being cut off if the [DropdownMenu] contains too many items.
        // See: https://github.com/JetBrains/compose-jb/issues/1388
        val popupPositionProvider = DesktopDropdownMenuPositionProvider(
            offset,
            density
        ) { parentBounds, menuBounds ->
            transformOriginState.value = calculateTransformOrigin(parentBounds, menuBounds)
        }

        var focusManager: FocusManager? by mutableStateOf(null)
        var inputModeManager: InputModeManager? by mutableStateOf(null)
        Popup(
            focusable = focusable,
            onDismissRequest = onDismissRequest,
            popupPositionProvider = popupPositionProvider,
            onKeyEvent = {
                handlePopupOnKeyEvent(it, onDismissRequest, focusManager!!, inputModeManager!!)
            },
        ) {
            focusManager = LocalFocusManager.current
            inputModeManager = LocalInputModeManager.current

            GridDropdownMenuContent(
                columnSize = columnSize,
                expandedStates = expandedStates,
                transformOriginState = transformOriginState,
                modifier = modifier,
                content = content
            )
        }
    }
}

private fun calculateTransformOrigin(
    parentBounds: IntRect,
    menuBounds: IntRect
): TransformOrigin {
    val pivotX = when {
        menuBounds.left >= parentBounds.right -> 0f
        menuBounds.right <= parentBounds.left -> 1f
        menuBounds.width == 0 -> 0f
        else -> {
            val intersectionCenter =
                (
                        kotlin.math.max(parentBounds.left, menuBounds.left) +
                                kotlin.math.min(parentBounds.right, menuBounds.right)
                        ) / 2
            (intersectionCenter - menuBounds.left).toFloat() / menuBounds.width
        }
    }
    val pivotY = when {
        menuBounds.top >= parentBounds.bottom -> 0f
        menuBounds.bottom <= parentBounds.top -> 1f
        menuBounds.height == 0 -> 0f
        else -> {
            val intersectionCenter =
                (
                        kotlin.math.max(parentBounds.top, menuBounds.top) +
                                kotlin.math.min(parentBounds.bottom, menuBounds.bottom)
                        ) / 2
            (intersectionCenter - menuBounds.top).toFloat() / menuBounds.height
        }
    }
    return TransformOrigin(pivotX, pivotY)
}

@ExperimentalComposeUiApi
private fun handlePopupOnKeyEvent(
    keyEvent: androidx.compose.ui.input.key.KeyEvent,
    onDismissRequest: () -> Unit,
    focusManager: FocusManager,
    inputModeManager: InputModeManager
): Boolean {
    return if (keyEvent.type == KeyEventType.KeyDown && keyEvent.awtEventOrNull?.keyCode == KeyEvent.VK_ESCAPE) {
        onDismissRequest()
        true
    } else if (keyEvent.type == KeyEventType.KeyDown) {
        when {
            keyEvent.isDirectionDown || keyEvent.isDirectionRight -> {
                inputModeManager.requestInputMode(InputMode.Keyboard)
                focusManager.moveFocus(FocusDirection.Next)
                true
            }
            keyEvent.isDirectionUp || keyEvent.isDirectionLeft -> {
                inputModeManager.requestInputMode(InputMode.Keyboard)
                focusManager.moveFocus(FocusDirection.Previous)
                true
            }
            else -> false
        }
    } else {
        false
    }
}

@Immutable
private data class DesktopDropdownMenuPositionProvider(
    val contentOffset: DpOffset,
    val density: Density,
    val onPositionCalculated: (IntRect, IntRect) -> Unit = { _, _ -> }
) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        // The min margin above and below the menu, relative to the screen.
        val verticalMargin = with(density) { MenuVerticalMargin.roundToPx() }
        // The content offset specified using the dropdown offset parameter.
        val contentOffsetX = with(density) { contentOffset.x.roundToPx() }
        val contentOffsetY = with(density) { contentOffset.y.roundToPx() }

        // Compute horizontal position.
        val toRight = anchorBounds.left + contentOffsetX
        val toLeft = anchorBounds.right - contentOffsetX - popupContentSize.width
        val toDisplayRight = windowSize.width - popupContentSize.width
        val toDisplayLeft = 0
        val x = if (layoutDirection == LayoutDirection.Ltr) {
            sequenceOf(toRight, toLeft, toDisplayRight)
        } else {
            sequenceOf(toLeft, toRight, toDisplayLeft)
        }.firstOrNull {
            it >= 0 && it + popupContentSize.width <= windowSize.width
        } ?: toLeft

        // Compute vertical position.
        val toBottom = maxOf(anchorBounds.bottom + contentOffsetY, verticalMargin)
        val toTop = anchorBounds.top - contentOffsetY - popupContentSize.height
        val toCenter = anchorBounds.top - popupContentSize.height / 2
        val toDisplayBottom = windowSize.height - popupContentSize.height - verticalMargin
        var y = sequenceOf(toBottom, toTop, toCenter, toDisplayBottom).firstOrNull {
            it >= verticalMargin &&
                    it + popupContentSize.height <= windowSize.height - verticalMargin
        } ?: toTop

        // Desktop specific vertical position checking
        val aboveAnchor = anchorBounds.top + contentOffsetY
        val belowAnchor = windowSize.height - anchorBounds.bottom - contentOffsetY

        if (belowAnchor >= aboveAnchor) {
            y = anchorBounds.bottom + contentOffsetY
        }

        if (y + popupContentSize.height > windowSize.height) {
            y = windowSize.height - popupContentSize.height
        }

        y = y.coerceAtLeast(0)

        onPositionCalculated(
            anchorBounds,
            IntRect(x, y, x + popupContentSize.width, y + popupContentSize.height)
        )
        return IntOffset(x, y)
    }
}
