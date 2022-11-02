package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.core.common.DateFormatType
import com.finance_tracker.finance_tracker.core.common.hexToColor
import com.finance_tracker.finance_tracker.domain.models.*
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.financetracker.financetracker.TransactionsEntityQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class TransactionsRepository(
    private val transactionsEntityQueries: TransactionsEntityQueries
) {

    suspend fun getAllTransactions(): List<Transaction> {
        return withContext(Dispatchers.IO) {
            transactionsEntityQueries.getAllFullTransactions {
                    id, type, amount, amountCurrency, categoryId,
                    accountId, _, date, _, accountType, accountName, balance, accountColorHex, currency,
                    _, categoryName, categoryIcon, _, _, _ ->
                Transaction(
                    id = id,
                    type = type,
                    amountCurrency = amountCurrency,
                    account = Account(
                        id = accountId ?: 0L,
                        type = accountType,
                        color = accountColorHex.hexToColor(),
                        name = accountName,
                        balance = balance,
                        currency = Currency.getByName(currency)
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