package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable

abstract class DesktopContext

actual typealias Context = DesktopContext

object EmptyContext: DesktopContext()

@Composable
actual fun getContext(): Context = EmptyContext