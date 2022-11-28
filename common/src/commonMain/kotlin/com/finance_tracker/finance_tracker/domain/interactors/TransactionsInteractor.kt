package com.finance_tracker.finance_tracker.domain.interactors

import androidx.paging.insertSeparators
import app.cash.paging.PagingData
import app.cash.paging.map
import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.data.repositories.TransactionsRepository
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import java.util.Date

class TransactionsInteractor(
    private val transactionsRepository: TransactionsRepository,
    private val accountsRepository: AccountsRepository,
) {

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

    fun getPaginatedTransactions(): Flow<PagingData<TransactionListModel>> {
        return transactionsRepository.getPaginatedTransactions()
            .map {
                it.map { TransactionListModel.Data(it) }
            }
            .map {
                insertSeparators(it)
            }
    }

    fun getPaginatedTransactionsByAccountId(id: Long): Flow<PagingData<TransactionListModel>> {
        return transactionsRepository.getPaginatedTransactionsByAccountId(id)
            .map {
                it.map { TransactionListModel.Data(it) }
            }
            .map {
                insertSeparators(it)
            }
    }

    private fun insertSeparators(
        pagingData: PagingData<TransactionListModel.Data>
    ): PagingData<TransactionListModel> {
        return pagingData.insertSeparators { data: TransactionListModel.Data?,
                                      data2: TransactionListModel.Data? ->
            val transaction = data?.transaction
            val transaction2 = data2?.transaction ?: return@insertSeparators null
            if (transaction == null || !transaction.date.isCalendarDateEquals(transaction2.date)) {
                val totalIncomeAmount =
                    transactionsRepository.getTotalTransactionAmountByDateAndType(
                        date = transaction2.date,
                        type = TransactionType.Income
                    )
                val totalExpenseAmount =
                    transactionsRepository.getTotalTransactionAmountByDateAndType(
                        date = transaction2.date,
                        type = TransactionType.Expense
                    )
                TransactionListModel.DateAndDayTotal(
                    date = transaction2.date,
                    income = totalIncomeAmount,
                    expense = totalExpenseAmount
                ) // получение общего дохода и расхода за 1 день
            } else {
                null
            }
        }
    }
}