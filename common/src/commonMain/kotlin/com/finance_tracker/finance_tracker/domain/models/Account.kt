package com.finance_tracker.finance_tracker.domain.models

import androidx.compose.ui.graphics.Color

data class Account(
    val id: Long,
    val type: Type,
    val name: String,
    val balance: Double,
    val color: Color
) {
    enum class Type {
        DebitCard,
        CreditCard,
        Cash
    }
}