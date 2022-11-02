package com.finance_tracker.finance_tracker.data.broadcast_receivers.parsers.models

import com.finance_tracker.finance_tracker.domain.models.TransactionType

typealias TransactionFields = Map<TransactionField, String>

fun TransactionFields.getAmount(): Double {
    return this[TransactionField.Amount]?.toDoubleOrNull() ?: 0.0
}

fun TransactionFields.getAmountCurrency(): String {
    return this[TransactionField.AmountCurrency].toString()
}

fun TransactionFields.getCardNumber(): String? {
    return this[TransactionField.CardNumber]
}

fun TransactionFields.getTransactionType(): TransactionType? {
    return when (this[TransactionField.Type]) {
        "Снятие", "Покупка" -> TransactionType.Expense
        "Пополнение" -> TransactionType.Income
        else -> null
    }
}