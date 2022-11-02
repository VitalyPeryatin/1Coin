package com.finance_tracker.finance_tracker.presentation.home

import com.adeo.kviewmodel.KViewModel
import com.finance_tracker.finance_tracker.core.common.EventChannel
import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.domain.models.Account
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: AccountsRepository
): KViewModel() {

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts = _accounts.asStateFlow()

    private val _events = EventChannel<HomeEvent>()
    val events = _events.receiveAsFlow()

    fun onScreenComposed() {
        loadAccounts()
    }

    private fun loadAccounts() {
        val oldAccountsCount = _accounts.value.size
        viewModelScope.launch {
            _accounts.value  = repository.getAllAccountsFromDatabase()
            val newAccountsCount = _accounts.value.size
            if (oldAccountsCount in 1 until newAccountsCount) {
                _events.send(HomeEvent.ScrollToItemAccounts(newAccountsCount - 1))
            }
        }
    }
}