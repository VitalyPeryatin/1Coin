package com.finance_tracker.finance_tracker.data.database.mappers

import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.financetracker.financetracker.TransactionsEntity

fun TransactionsEntity.transactionToDomainModel(): Transaction {
    return Transaction(
        id = id,
        type = type,
        amountCurrency = Currency.default,
        account = Account.EMPTY,
        amount = amount,
        category = Category.EMPTY,
        date = date
    )
}