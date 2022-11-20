package com.finance_tracker.finance_tracker.presentation.transactions

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import com.adeo.kviewmodel.KViewModel
import com.finance_tracker.finance_tracker.data.data_sources.TransactionSource
import com.finance_tracker.finance_tracker.domain.interactors.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val PageSize = 20
class TransactionsViewModel(
    private val transactionsInteractor: TransactionsInteractor
): KViewModel() {

    private val _transactions: MutableStateFlow<List<TransactionListModel>> = MutableStateFlow(emptyList())
    val transactions: StateFlow<List<TransactionListModel>> = _transactions.asStateFlow()

    val transactionsPaginated: Flow<PagingData<TransactionListModel>> = Pager(PagingConfig(pageSize = PageSize)) {
        TransactionSource(transactionsInteractor)
    }.flow.cachedIn(viewModelScope)

    //private var loadTransactionsJob: Job? = null

    fun loadTransactions() {
        /*loadTransactionsJob?.cancel()
        loadTransactionsJob = viewModelScope.launch {
            _transactions.update { transactionsPaginated }
        }*/
    }

    fun onDeleteTransactions(
        transactions: List<TransactionListModel.Data>
    ) {
        viewModelScope.launch {
            transactionsInteractor.deleteTransactions(
                transactions = transactions.map { it.transaction }
            )
            /*loadTransactions()*/
        }
    }
}