package com.finance_tracker.finance_tracker.domain.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.util.*

sealed interface TransactionListModel {

    data class DateAndDayTotal(
        val date: Date,
        val income: Double,
        val expense: Double
    ): TransactionListModel

    data class Data(
        val transaction: Transaction,
        val isSelected: MutableState<Boolean> = mutableStateOf(false)
    ): TransactionListModel
}