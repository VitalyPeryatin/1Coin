package com.finance_tracker.finance_tracker.presentation.detail_account

import androidx.paging.cachedIn
import com.adeo.kviewmodel.KViewModel
import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.domain.interactors.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.models.Account
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailAccountViewModel(
    private val transactionsInteractor: TransactionsInteractor,
    private val account: Account,
    private val accountsRepository: AccountsRepository
): KViewModel() {

    val paginatedTransactions = transactionsInteractor.getPaginatedTransactionsByAccountId(account.id)
        .cachedIn(viewModelScope)


    private val _accountData = MutableStateFlow(account)
    val accountData = _accountData.asStateFlow()
    fun onScreenComposed() {
        loadAccount(account.id)
    }

    private fun loadAccount(id: Long) {
        viewModelScope.launch {
            _accountData.value = accountsRepository.getAccountById(id)
        }
    }
}