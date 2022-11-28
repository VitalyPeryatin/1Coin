package com.finance_tracker.finance_tracker.presentation.detail_account

import androidx.paging.cachedIn
import com.adeo.kviewmodel.KViewModel
import com.finance_tracker.finance_tracker.domain.interactors.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.models.Account

class DetailAccountViewModel(
    private val transactionsInteractor: TransactionsInteractor,
    private val account: Account
): KViewModel() {

    val paginatedTransactions = transactionsInteractor.getPaginatedTransactionsByAccountId(account.id)
        .cachedIn(viewModelScope)
}