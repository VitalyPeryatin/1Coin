package com.finance_tracker.finance_tracker.core.ui

import android.app.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.common.R
import com.finance_tracker.finance_tracker.core.common.LocalContext
import java.time.LocalDate

private val todayDate = LocalDate.now()

@Composable
actual fun CalendarDialog(
    modifier: Modifier,
    onControllerCreate: (CalendarDialogController) -> Unit,
    onDateChangeListener: (LocalDate) -> Unit
) {
    val context = LocalContext.current
    val datePickerDialog = DatePickerDialog(
        context, R.style.CalendarDialogTheme,
        { _, year, month, dayOfMonth ->
            onDateChangeListener.invoke(LocalDate.of(year, month + 1, dayOfMonth))
        }, todayDate.year, todayDate.monthValue, todayDate.dayOfMonth
    )
    datePickerDialog.window?.setBackgroundDrawableResource(R.drawable.calendar_dialog_background)
    LaunchedEffect(datePickerDialog) {
        onControllerCreate.invoke(getCalendarDialogController(datePickerDialog))
    }
}

private fun getCalendarDialogController(datePickerDialog: DatePickerDialog): CalendarDialogController {
    return object : CalendarDialogController {
        override fun show() {
            datePickerDialog.show()
        }

        override fun cancel() {
            datePickerDialog.cancel()
        }
    }
}