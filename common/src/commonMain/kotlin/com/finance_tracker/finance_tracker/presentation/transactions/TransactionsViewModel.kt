package com.finance_tracker.finance_tracker.presentation.transactions

import com.finance_tracker.finance_tracker.core.common.ViewModel
import com.finance_tracker.finance_tracker.data.repositories.TransactionsRepository
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class TransactionsViewModel constructor(
    private val transactionsRepository: TransactionsRepository
): ViewModel() {

    private val _transactions: MutableStateFlow<List<TransactionUiModel>> = MutableStateFlow(emptyList())
    val transactions: StateFlow<List<TransactionUiModel>> = _transactions.asStateFlow()

    init {
        loadTransactions()
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            val transactions = transactionsRepository.getAllTransactions()
            for (transaction in transactions) {
                val lastUiTransactionModel = _transactions.value.lastOrNull()

                if (lastUiTransactionModel == null ||
                    (lastUiTransactionModel is TransactionUiModel.Data &&
                            !lastUiTransactionModel.transaction.date.isCalendarDateEquals(
                                transaction.date
                            ))
                ) {
                    _transactions.update {
                        val totalIncomeAmount =
                            transactionsRepository.getTotalTransactionAmountByDateAndType(
                                date = transaction.date,
                                type = TransactionType.Income
                            )
                        val totalExpenseAmount =
                            transactionsRepository.getTotalTransactionAmountByDateAndType(
                                date = transaction.date,
                                type = TransactionType.Expense
                            )
                        it + TransactionUiModel.DateAndDayTotal(
                            date = transaction.date,
                            income = totalIncomeAmount,
                            expense = totalExpenseAmount
                        ) // получение общего дохода и расхода за 1 день
                    }
                }
                _transactions.update { it + TransactionUiModel.Data(transaction) }
            }
        }
    }

    private fun Date?.isCalendarDateEquals(date: Date?): Boolean {
        if (this == date) return true
        if (this == null) return false
        if (date == null) return false

        val calendar1 = Calendar.getInstance().apply { time = this@isCalendarDateEquals }
        val calendar2 = Calendar.getInstance().apply { time = date }
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
    }
}