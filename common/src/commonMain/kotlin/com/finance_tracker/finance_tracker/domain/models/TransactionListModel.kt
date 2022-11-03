package com.finance_tracker.finance_tracker.domain.models

import java.util.*

sealed interface TransactionListModel {

    data class DateAndDayTotal(
        val date: Date,
        val income: Double,
        val expense: Double
    ): TransactionListModel

    data class Data(
        val transaction: Transaction
    ): TransactionListModel
}