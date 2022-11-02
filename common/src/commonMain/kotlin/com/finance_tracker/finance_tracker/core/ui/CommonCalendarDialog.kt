package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.time.LocalDate

@Composable
expect fun CalendarDialog(
    modifier: Modifier = Modifier,
    onControllerCreate: (CalendarDialogController) -> Unit = { StubCalendarDialogController },
    onDateChangeListener: (LocalDate) -> Unit = {}
)

interface CalendarDialogController {

    fun show()

    fun cancel()
}

object StubCalendarDialogController: CalendarDialogController {

    override fun show() {
    }

    override fun cancel() {
    }
}