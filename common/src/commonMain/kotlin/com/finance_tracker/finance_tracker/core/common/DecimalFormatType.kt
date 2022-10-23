package com.finance_tracker.finance_tracker.core.common

import java.text.DecimalFormat

sealed class DecimalFormatType(pattern: String): DecimalFormat(pattern) {
    object Amount: DecimalFormatType("#.##")
}
