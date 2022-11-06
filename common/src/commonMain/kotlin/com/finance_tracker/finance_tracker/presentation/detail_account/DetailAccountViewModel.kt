package com.finance_tracker.finance_tracker.presentation.detail_account

import com.adeo.kviewmodel.KViewModel
import com.finance_tracker.finance_tracker.domain.interactors.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailAccountViewModel(
    private val account: Account,
    private val transactionsInteractor: TransactionsInteractor
): KViewModel() {

    private val _transactions: MutableStateFlow<List<TransactionListModel>> = MutableStateFlow(emptyList())
    val transactions: StateFlow<List<TransactionListModel>> = _transactions.asStateFlow()

    private var loadTransactionsJob: Job? = null

    fun onScreenComposed() {
        loadTransactions()
    }

    private fun loadTransactions() {
        loadTransactionsJob?.cancel()
        loadTransactionsJob = viewModelScope.launch {
            _transactions.update {
                transactionsInteractor.getTransactions(
                    accountId = account.id
                )
            }
        }
    }
}