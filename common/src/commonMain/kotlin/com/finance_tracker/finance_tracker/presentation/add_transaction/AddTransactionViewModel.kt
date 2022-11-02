package com.finance_tracker.finance_tracker.presentation.add_transaction

import com.adeo.kviewmodel.KViewModel
import com.finance_tracker.finance_tracker.data.database.mappers.accountToDomainModel
import com.finance_tracker.finance_tracker.data.database.mappers.categoryToDomainModel
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.finance_tracker.finance_tracker.presentation.add_transaction.views.EnterTransactionStep
import com.finance_tracker.finance_tracker.presentation.add_transaction.views.enter_transaction_controller.KeyboardCommand
import com.financetracker.financetracker.AccountsEntityQueries
import com.financetracker.financetracker.CategoriesEntityQueries
import com.financetracker.financetracker.TransactionsEntityQueries
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*

class AddTransactionViewModel(
    private val transactionsEntityQueries: TransactionsEntityQueries,
    private val accountsEntityQueries: AccountsEntityQueries,
    private val categoriesEntityQueries: CategoriesEntityQueries
): KViewModel() {

    private val _accounts: MutableStateFlow<List<Account>> = MutableStateFlow(emptyList())
    val accounts: StateFlow<List<Account>> = _accounts.asStateFlow()

    private val _selectedAccount: MutableStateFlow<Account?> = MutableStateFlow(null)
    val selectedAccount: StateFlow<Account?> = _selectedAccount.asStateFlow()

    private val _selectedCategory: MutableStateFlow<Category?> = MutableStateFlow(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory.asStateFlow()

    private val _selectedDate: MutableStateFlow<LocalDate> = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val _amount: MutableStateFlow<String> = MutableStateFlow("0")
    val amount: StateFlow<String> = _amount.asStateFlow()

    private val _expenseCategories: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    val expenseCategories: StateFlow<List<Category>> = _expenseCategories.asStateFlow()

    private val _incomeCategories: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    val incomeCategories: StateFlow<List<Category>> = _incomeCategories.asStateFlow()

    private val steps = EnterTransactionStep.values()
    val firstStep = steps.first()

    private val _selectedTransactionType: MutableStateFlow<TransactionType> = MutableStateFlow(TransactionType.Expense)
    val selectedTransactionType: StateFlow<TransactionType> = _selectedTransactionType.asStateFlow()

    fun onScreenComposed() {
        loadAccounts()
        loadCategories()
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            _accounts.value = accountsEntityQueries.getAllAccounts().executeAsList()
                .map { it.accountToDomainModel() }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _expenseCategories.value = categoriesEntityQueries.getAllExpenseCategories().executeAsList()
                .map { it.categoryToDomainModel() }
            _incomeCategories.value = categoriesEntityQueries.getAllIncomeCategories().executeAsList()
                .map { it.categoryToDomainModel() }
        }
    }

    fun addTransaction(transaction: Transaction) {
        transactionsEntityQueries.insertTransaction(
            id = null,
            type = transaction.type,
            amount = transaction.amount,
            amountCurrency = transaction.amountCurrency,
            categoryId = transaction.category?.id,
            accountId = transaction.account.id,
            insertionDate = Date(),
            date = transaction.date,
        )
    }

    fun onAccountSelect(account: Account) {
        _selectedAccount.value = account
    }

    fun onCategorySelect(category: Category) {
        _selectedCategory.value = category
    }

    fun onDateSelect(date: LocalDate) {
        _selectedDate.value = date
    }

    fun onKeyboardButtonClick(command: KeyboardCommand) {
        val amountText = _amount.value
        var newAmountText = amountText
        when (command) {
            KeyboardCommand.Delete -> {
                when {
                    amountText.length <= 1 && amountText.toDouble() == 0.0 -> {
                        /* ignore */
                    }

                    amountText.length <= 1 && amountText.toDouble() != 0.0 -> {
                        newAmountText = "0"
                    }

                    else -> {
                        newAmountText = newAmountText.dropLast(1)
                    }
                }
            }

            is KeyboardCommand.Digit -> {
                when (amountText) {
                    "0" -> {
                        newAmountText = command.value.toString()
                    }
                    else -> {
                        newAmountText += command.value.toString()
                    }
                }
            }

            KeyboardCommand.Point -> {
                if (!newAmountText.contains(".")) {
                    newAmountText += "."
                }
            }
        }
        if (newAmountText.matches(Regex("^\\d*\\.?\\d*"))) {
            _amount.value = newAmountText
        }
    }

    fun onTransactionTypeSelect(transactionType: TransactionType) {
        _selectedTransactionType.value = transactionType
    }
}