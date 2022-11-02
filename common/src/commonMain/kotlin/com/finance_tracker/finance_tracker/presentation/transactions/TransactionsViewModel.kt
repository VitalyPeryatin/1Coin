package com.finance_tracker.finance_tracker.presentation.transactions

import com.adeo.kviewmodel.KViewModel
import com.finance_tracker.finance_tracker.data.repositories.TransactionsRepository
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

class TransactionsViewModel constructor(
    private val transactionsRepository: TransactionsRepository
): KViewModel() {

    private val _transactions: MutableStateFlow<List<TransactionUiModel>> = MutableStateFlow(emptyList())
    val transactions: StateFlow<List<TransactionUiModel>> = _transactions.asStateFlow()

    private var loadTransactionsJob: Job? = null

    fun loadTransactions() {
        loadTransactionsJob?.cancel()
        loadTransactionsJob = viewModelScope.launch {
            val allTransactions = transactionsRepository.getAllTransactions()
            val newTransactions = mutableListOf<TransactionUiModel>()
            for (transaction in allTransactions) {
                val lastUiTransactionModel = newTransactions.lastOrNull()

                if (lastUiTransactionModel == null ||
                    (lastUiTransactionModel is TransactionUiModel.Data &&
                            !lastUiTransactionModel.transaction.date.isCalendarDateEquals(
                                transaction.date
                            ))
                ) {
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
                    newTransactions += TransactionUiModel.DateAndDayTotal(
                        date = transaction.date,
                        income = totalIncomeAmount,
                        expense = totalExpenseAmount
                    ) // получение общего дохода и расхода за 1 день
                }
                newTransactions += TransactionUiModel.Data(transaction)
            }
            _transactions.update { newTransactions }
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