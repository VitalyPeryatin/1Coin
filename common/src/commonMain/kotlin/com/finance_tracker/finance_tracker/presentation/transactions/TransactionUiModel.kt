package com.finance_tracker.finance_tracker.presentation.transactions

import com.finance_tracker.finance_tracker.domain.models.Transaction
import java.util.Date

sealed interface TransactionUiModel {

    data class DateAndDayTotal(
        val date: Date,
        val income: Double,
        val expense: Double
    ): TransactionUiModel

    data class Data(
        val transaction: Transaction
    ): TransactionUiModel
}