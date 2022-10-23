package com.finance_tracker.finance_tracker.domain.models

import java.util.Date

data class Transaction(
    val id: Long? = null,
    val type: TransactionType,
    val amountCurrency: String,
    val account: Account,
    val amount: Double = 0.0,
    val category: Category? = null,
    val date: Date
)