package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.lgooddatepicker.components.DatePicker
import com.github.lgooddatepicker.components.DatePickerSettings
import com.github.lgooddatepicker.optionalusertools.DateChangeListener
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Composable
@Suppress("MissingModifierDefaultValue", "ModifierParameterPosition")
actual fun CalendarDialog(
    minDate: Long,
    maxDate: Long,
    modifier: Modifier,
    onControllerCreate: (CalendarDialogController) -> Unit,
    onDateChangeListener: (LocalDate) -> Unit
) {

    val datePickerSettings: DatePickerSettings by remember {
        mutableStateOf(
            DatePickerSettings().apply {
                visibleClearButton = false
            }
        )
    }
    val datePicker: DatePicker by remember(datePickerSettings) {
        mutableStateOf(DatePicker(datePickerSettings))
    }
    LaunchedEffect(datePicker) {
        onControllerCreate.invoke(getCalendarDialogController(datePicker))
    }
    SwingPanel(
        modifier = Modifier.size(0.dp),
        background = Color.Red,
        factory = { datePicker }
    )
    LaunchedEffect(datePickerSettings) {
        datePickerSettings.setDateRangeLimits(minDate.toLocalDate(), maxDate.toLocalDate())
    }
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

@Suppress("NewApi")
private fun Long.toLocalDate(): LocalDate {
    val calendar = Calendar.getInstance().apply { time = Date(this@toLocalDate) }
    return LocalDateTime.ofInstant(calendar.toInstant(), calendar.timeZone.toZoneId()).toLocalDate()
}