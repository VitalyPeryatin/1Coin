package com.finance_tracker.finance_tracker.presentation.transactions

import androidx.paging.cachedIn
import com.finance_tracker.finance_tracker.core.common.view_models.BaseViewModel
import com.finance_tracker.finance_tracker.domain.interactors.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import kotlinx.coroutines.launch

class TransactionsViewModel(
    private val transactionsInteractor: TransactionsInteractor
): BaseViewModel<TransactionsAction>() {

    val paginatedTransactions = transactionsInteractor.getPaginatedTransactions()
        .cachedIn(viewModelScope)

    fun onDeleteTransactions(
        transactions: List<TransactionListModel.Data>
    ) {
        viewModelScope.launch {
            transactionsInteractor.deleteTransactions(
                transactions = transactions.map { it.transaction }
            )
            viewAction = TransactionsAction.RefreshTransactions
        }
    }
}