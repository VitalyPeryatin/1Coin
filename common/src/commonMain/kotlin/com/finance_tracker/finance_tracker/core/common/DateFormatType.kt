package com.finance_tracker.finance_tracker.core.common

import java.text.SimpleDateFormat

sealed class DateFormatType(pattern: String): SimpleDateFormat(pattern) {
    object CommonDateFormat: SimpleDateFormat("yyyy-MM-dd")
}