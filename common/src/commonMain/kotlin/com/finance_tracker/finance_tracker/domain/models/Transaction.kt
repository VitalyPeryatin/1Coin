package com.finance_tracker.finance_tracker.domain.models

import java.util.*

data class Transaction(
    val id: Long? = null,
    val type: TransactionType,
    val amountCurrency: Currency,
    val account: Account,
    val amount: Double = 0.0,
    val category: Category? = null,
    val date: Date
) {
    companion object {
        val EMPTY = Transaction(
            id = null,
            type = TransactionType.Expense,
            amountCurrency = Currency.default,
            account = Account.EMPTY,
            amount = 0.0,
            category = null,
            date = Date()
        )
    }
}