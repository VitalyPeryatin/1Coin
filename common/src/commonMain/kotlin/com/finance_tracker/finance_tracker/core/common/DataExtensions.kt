package com.finance_tracker.finance_tracker.core.common

import java.time.LocalDate
import java.util.*

fun Date.toLocalDate(): LocalDate {
    val calendar = Calendar.getInstance().apply {
        time = this@toLocalDate
    }
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    return LocalDate.of(year, month, dayOfMonth)
}