package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.core.common.hexToColor
import com.finance_tracker.finance_tracker.data.database.mappers.accountToDomainModel
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.AccountColorData
import com.financetracker.financetracker.AccountColorsEntityQueries
import com.financetracker.financetracker.AccountsEntityQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountsRepository(
    private val accountsEntityQueries: AccountsEntityQueries,
    private val accountColorsEntityQueries: AccountColorsEntityQueries
) {

    suspend fun insertAccount(
        accountName: String,
        type: Account.Type,
        balance: Double,
        colorHex: String
    ) {
        withContext(Dispatchers.IO) {
            accountsEntityQueries.insertAccount(
                id = null,
                type = type,
                name = accountName,
                balance = balance,
                colorHex = colorHex
            )
        }
    }

    suspend fun getAllAccountColors(): List<AccountColorData> {
        return withContext(Dispatchers.IO) {
            accountColorsEntityQueries.getAllAccountColors { hex, name ->
                AccountColorData(
                    color = hex.hexToColor(),
                    name = name
                )
            }.executeAsList()
        }
    }

    suspend fun getAllAccountsFromDatabase(): List<Account> {
        return withContext(Dispatchers.IO) {
            accountsEntityQueries.getAllAccounts().executeAsList()
                .map { it.accountToDomainModel() }
        }
    }
}