package com.finance_tracker.finance_tracker.presentation.accounts

import com.adeo.kviewmodel.KViewModel
import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.domain.models.Account
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountsViewModel(
    private val repository: AccountsRepository
): KViewModel() {

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts = _accounts.asStateFlow()

    fun onScreenComposed() {
        loadAccounts()
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            _accounts.value = repository.getAllAccountsFromDatabase()
        }
    }
}
