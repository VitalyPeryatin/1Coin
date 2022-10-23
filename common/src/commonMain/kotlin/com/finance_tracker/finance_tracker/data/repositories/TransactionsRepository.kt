package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.core.common.DateFormatType
import com.finance_tracker.finance_tracker.core.common.hexToColor
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.financetracker.financetracker.TransactionsEntityQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

class TransactionsRepository(
    private val transactionsEntityQueries: TransactionsEntityQueries
) {

    suspend fun getAllTransactions(): List<Transaction> {
        return withContext(Dispatchers.IO) {
            transactionsEntityQueries.getAllFullTransactions {
                    id, type, amount, amountCurrency, categoryId,
                    accountId, date, _, accountName, _, balance, accountColorHex,
                    _, categoryName, categoryIcon ->
                Transaction(
                    id = id,
                    type = type,
                    amountCurrency = amountCurrency,
                    account = Account(
                        id = accountId ?: 0L,
                        type = Account.Type.Cash,
                        color = accountColorHex.hexToColor(),
                        name = accountName.name,
                        balance = balance
                    ),
                    category = categoryId?.let {
                        Category(
                            id = categoryId,
                            name = categoryName,
                            iconId = categoryIcon
                        )
                    },
                    amount = amount,
                    date = date
                )
            }.executeAsList()
        }
    }

    suspend fun getTotalTransactionAmountByDateAndType(
        date: Date,
        type: TransactionType
    ): Double {
        return withContext(Dispatchers.IO) {
            transactionsEntityQueries.getTotalTransactionAmountByDateAndType(
                date = DateFormatType.CommonDateFormat.format(date),
                type = type
            ).executeAsOneOrNull()?.SUM ?: 0.0
        }
    }
}