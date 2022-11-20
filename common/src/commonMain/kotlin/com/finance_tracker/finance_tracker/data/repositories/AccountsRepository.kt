package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.core.common.hexToColor
import com.finance_tracker.finance_tracker.data.database.mappers.accountToDomainModel
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.AccountColorData
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.financetracker.financetracker.data.AccountColorsEntityQueries
import com.financetracker.financetracker.data.AccountsEntityQueries
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
        colorHex: String,
        currency: Currency
    ) {
        withContext(Dispatchers.IO) {
            accountsEntityQueries.insertAccount(
                id = null,
                type = type,
                name = accountName,
                balance = balance,
                colorHex = colorHex,
                currency = currency.name
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

    suspend fun increaseAccountBalance(id: Long, value: Double) {
        withContext(Dispatchers.IO) {
            accountsEntityQueries.increaseBalanceByAccountId(value, id)
        }
    }

    suspend fun reduceAccountBalance(id: Long, value: Double) {
        withContext(Dispatchers.IO) {
            accountsEntityQueries.reduceBalanceByAccountId(value, id)
        }
    }

    suspend fun updateAccount(
        type: Account.Type,
        name: String,
        balance: Double,
        colorHex: String,
        currency: Currency,
        id: Long
    ) {
        withContext(Dispatchers.IO) {
            accountsEntityQueries.updateAccountById(
                type = type,
                name = name,
                balance = balance,
                colorHex = colorHex,
                currency = currency.name,
                id = id,
            )
        }
    }

    suspend fun getAccountById(id: Long): Account {
        return withContext(Dispatchers.IO) {
            accountsEntityQueries.getAccountById(id).executeAsOne().accountToDomainModel()
        }
    }

    suspend fun deleteAccountById(id: Long) {
        withContext(Dispatchers.IO) {
            accountsEntityQueries.deleteAccountById(id)
        }
    }
}