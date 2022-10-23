package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Composable
fun Dp.toPx(): Float {
    return LocalDensity.current.run { this@toPx.toPx() }
}

@Composable
fun Int.toDp(): Dp {
    return LocalDensity.current.run { this@toDp.toDp() }
}

@Composable
fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }

@Composable
fun Dp.asSp() = with(LocalDensity.current) { toSp() }

@Composable
fun spToDp(sp: TextUnit) = with(LocalDensity.current) { sp.toDp() }

@Composable
fun TextUnit.asDp() = with(LocalDensity.current) { toDp() }