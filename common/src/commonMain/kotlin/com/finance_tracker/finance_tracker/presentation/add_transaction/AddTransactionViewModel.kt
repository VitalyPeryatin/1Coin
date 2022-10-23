package com.finance_tracker.finance_tracker.presentation.add_transaction

import com.finance_tracker.finance_tracker.core.common.ViewModel
import com.finance_tracker.finance_tracker.data.database.mappers.accountToDomainModel
import com.finance_tracker.finance_tracker.data.database.mappers.categoryToDomainModel
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.financetracker.financetracker.AccountsEntityQueries
import com.financetracker.financetracker.CategoriesEntityQueries
import com.financetracker.financetracker.TransactionsEntityQueries
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddTransactionViewModel(
    private val transactionsEntityQueries: TransactionsEntityQueries,
    private val accountsEntityQueries: AccountsEntityQueries,
    private val categoriesEntityQueries: CategoriesEntityQueries
): ViewModel() {

    private val _accounts: MutableStateFlow<List<Account>> = MutableStateFlow(emptyList())
    val accounts: StateFlow<List<Account>> = _accounts.asStateFlow()

    private val _categories: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    init {
        loadAccounts()
        loadCategories()
    }

    private fun loadAccounts() {
        _accounts.value = accountsEntityQueries.getAllAccounts().executeAsList()
            .map { it.accountToDomainModel() }
    }

    private fun loadCategories() {
        _categories.value = categoriesEntityQueries.getAllCategories().executeAsList()
            .map { it.categoryToDomainModel() }
    }

    fun addTransaction(transaction: Transaction) {
        transactionsEntityQueries.insertTransaction(
            id = null,
            type = transaction.type,
            amount = transaction.amount,
            amountCurrency = transaction.amountCurrency,
            categoryId = transaction.category?.id,
            accountId = transaction.account.id,
            date = transaction.date
        )
    }
}