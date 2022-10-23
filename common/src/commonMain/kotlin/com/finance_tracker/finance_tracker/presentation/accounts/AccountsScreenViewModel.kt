package com.finance_tracker.finance_tracker.presentation.accounts

import com.finance_tracker.finance_tracker.core.common.ViewModel
import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.domain.models.Account
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountsScreenViewModel(
    private val repository: AccountsRepository
): ViewModel() {

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts = _accounts.asStateFlow()

    init {
        loadAccounts()
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            _accounts.value = repository.getAllAccountsFromDatabase()
        }
    }
}
