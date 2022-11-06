package com.finance_tracker.finance_tracker.presentation.home

import com.adeo.kviewmodel.KViewModel
import com.finance_tracker.finance_tracker.core.common.EventChannel
import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.domain.interactors.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val accountsRepository: AccountsRepository,
    private val transactionsInteractor: TransactionsInteractor
) : KViewModel() {

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts = _accounts.asStateFlow()

    private val _events = EventChannel<HomeEvent>()
    val events = _events.receiveAsFlow()

    private val _transactions = MutableStateFlow<List<TransactionListModel>>(emptyList())
    val transactions = _transactions.asStateFlow()
    init {
        getTransactions()
    }
    private fun getTransactions() {
        viewModelScope.launch {
            _transactions.value = transactionsInteractor.getAllTransactions()
        }
    }

    fun onScreenComposed() {
        loadAccounts()
    }

    private fun loadAccounts() {
        val oldAccountsCount = _accounts.value.size
        viewModelScope.launch {
            _accounts.value = accountsRepository.getAllAccountsFromDatabase()
            val newAccountsCount = _accounts.value.size
            if (oldAccountsCount in 1 until newAccountsCount) {
                _events.send(HomeEvent.ScrollToItemAccounts(newAccountsCount - 1))
            }
        }
    }
}