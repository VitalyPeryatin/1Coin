package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.lgooddatepicker.components.DatePicker
import com.github.lgooddatepicker.components.DatePickerSettings
import com.github.lgooddatepicker.optionalusertools.DateChangeListener
import java.time.LocalDate

@Composable
actual fun CalendarDialog(
    modifier: Modifier,
    onControllerCreate: (CalendarDialogController) -> Unit,
    onDateChangeListener: (LocalDate) -> Unit
) {

    val datePicker: DatePicker by remember {
        mutableStateOf(
            DatePicker(
                DatePickerSettings().apply {
                    visibleClearButton = false
                }
            )
        )
    }
    LaunchedEffect(datePicker) {
        onControllerCreate.invoke(getCalendarDialogController(datePicker))
    }
    SwingPanel(
        modifier = Modifier.size(0.dp),
        background = Color.Red,
        factory = { datePicker }
    )
    DisposableEffect(datePicker) {
        val dateChangeListener = DateChangeListener {
            onDateChangeListener.invoke(it.newDate)
        }
        datePicker.addDateChangeListener(dateChangeListener)
        onDispose {
            datePicker.removeDateChangeListener(dateChangeListener)
        }
    }
}

private fun getCalendarDialogController(datePicker: DatePicker): CalendarDialogController {
    return object : CalendarDialogController {
        override fun show() {
            if (!datePicker.isPopupOpen) {
                datePicker.openPopup()
            }
        }

        override fun cancel() {
            if (datePicker.isPopupOpen) {
                datePicker.closePopup()
            }
        }
    }
}