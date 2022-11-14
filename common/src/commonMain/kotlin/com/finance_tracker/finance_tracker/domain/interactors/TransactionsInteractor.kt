package com.finance_tracker.finance_tracker.domain.interactors

import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.data.repositories.TransactionsRepository
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import java.util.Calendar
import java.util.Date

class TransactionsInteractor(
    private val transactionsRepository: TransactionsRepository,
    private val accountsRepository: AccountsRepository,
) {

    suspend fun getTransactions(accountId: Long? = null): List<TransactionListModel> {
        val allTransactions = if (accountId == null) {
            transactionsRepository.getAllTransactions()
        } else {
            transactionsRepository.getTransactions(accountId)
        }
        val newTransactions = mutableListOf<TransactionListModel>()
        for (transaction in allTransactions) {
            val lastUiTransactionModel = newTransactions.lastOrNull()

            if (lastUiTransactionModel == null ||
                (lastUiTransactionModel is TransactionListModel.Data &&
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
                newTransactions += TransactionListModel.DateAndDayTotal(
                    date = transaction.date,
                    income = totalIncomeAmount,
                    expense = totalExpenseAmount
                ) // получение общего дохода и расхода за 1 день
            }
            newTransactions += TransactionListModel.Data(transaction)
        }
        return newTransactions
    }

    private suspend fun updateAccountBalance(transaction: Transaction) {
        if (transaction.type == TransactionType.Expense) {
            accountsRepository.increaseAccountBalance(transaction.account.id, transaction.amount)
        } else {
            accountsRepository.reduceAccountBalance(transaction.account.id, transaction.amount)
        }
    }

    suspend fun deleteTransactions(transactions: List<Transaction>) {
        transactionsRepository.deleteTransactions(transactions)

        transactions.forEach {
            updateAccountBalance(it)
        }
    }

    suspend fun addOrUpdateTransaction(transaction: Transaction) {
        transactionsRepository.addOrUpdateTransaction(transaction)
        updateAccountBalance(transaction)
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