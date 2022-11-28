package com.finance_tracker.finance_tracker.data.mappers

import com.finance_tracker.finance_tracker.core.common.hexToColor
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import java.util.Date

val fullTransactionMapper: (
    id: Long,
    type: TransactionType,
    amount: Double,
    amountCurrency: String,
    categoryId: Long?,
    accountId: Long?,
    insertionDate: Date,
    date: Date,
    id_: Long,
    type_: Account.Type,
    name: String,
    balance: Double,
    colorHex: String,
    currency: String,
    id__: Long,
    name_: String,
    icon: String,
    position: Long?,
    isExpense: Boolean,
    isIncome: Boolean
) -> Transaction = { id, type, amount, amountCurrency, categoryId,
                     accountId, _, date, _, accountType, accountName, balance, accountColorHex, currency,
                     _, categoryName, categoryIcon, _, _, _ ->
    Transaction(
        id = id,
        type = type,
        amountCurrency = Currency.getByName(amountCurrency),
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
}
