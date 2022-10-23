package com.finance_tracker.finance_tracker.data.database.mappers

import androidx.compose.ui.graphics.Color
import com.finance_tracker.finance_tracker.domain.models.Account
import com.financetracker.financetracker.AccountsEntity

fun AccountsEntity.accountToDomainModel(): Account {
    return Account(
        id = id,
        type = Account.Type.Cash,
        name = name,
        balance = balance,
        color = Color.Red
    )
}