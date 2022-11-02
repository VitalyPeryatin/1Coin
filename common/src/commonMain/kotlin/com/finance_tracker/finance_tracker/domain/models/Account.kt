package com.finance_tracker.finance_tracker.domain.models

import androidx.compose.ui.graphics.Color

data class Account(
    val id: Long,
    val type: Type,
    val name: String,
    val balance: Double,
    val color: Color,
    val currency: Currency
) {
    enum class Type(val textId: String) {
        DebitCard("account_type_debit_card"),
        CreditCard("account_type_credit_card"),
        Cash("account_type_cash")
    }
}