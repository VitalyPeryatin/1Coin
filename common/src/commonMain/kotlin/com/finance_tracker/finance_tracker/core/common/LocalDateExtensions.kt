package com.finance_tracker.finance_tracker.core.common

import java.time.LocalDate
import java.time.ZoneId
import java.util.*

fun LocalDate.toDate(): Date {
    return Date.from(atStartOfDay(ZoneId.systemDefault()).toInstant())
}

fun LocalDate.asCalendar(): Calendar {
    return Calendar.getInstance().apply { time = toDate() }
}