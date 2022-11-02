package com.finance_tracker.finance_tracker.data.database.mappers

import com.finance_tracker.finance_tracker.core.common.hexToColor
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.financetracker.financetracker.AccountsEntity

fun AccountsEntity.accountToDomainModel(): Account {
    return Account(
        id = id,
        type = type,
        name = name,
        balance = balance,
        color = colorHex.hexToColor(),
        currency = Currency.getByName(currency)
    )
}