package com.finance_tracker.finance_tracker.presentation.home

import com.adeo.kviewmodel.KViewModel
import com.finance_tracker.finance_tracker.core.common.EventChannel
import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.data.repositories.CurrenciesRepository
import com.finance_tracker.finance_tracker.domain.interactors.CurrencyConverterInteractor
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Currency
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class HomeViewModel(
    private val accountsRepository: AccountsRepository,
    private val currenciesRepository: CurrenciesRepository,
    private val currencyConverterInteractor: CurrencyConverterInteractor
): KViewModel() {

    private val currencyRatesFlow = currenciesRepository.getCurrencyRatesAsMap()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = mapOf())

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts = _accounts.asStateFlow()

    private val _totalAmount = MutableStateFlow(0.0)
    val totalAmount = _totalAmount.asStateFlow()

    private val _totalCurrency = MutableStateFlow(Currency.default)
    val totalCurrency = _totalCurrency.asStateFlow()

    private val _events = EventChannel<HomeEvent>()
    val events = _events.receiveAsFlow()

    private var loadTotalAmountJob: Job? = null

    private val baseCurrencyName = "USD" // TODO: Убрать моковые данные

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Napier.e("HomeViewModel", throwable)
    }

    init {
        updateCurrencyRates()
    }

    fun onScreenComposed() {
        loadAccounts()
        loadTotalAmount()
    }

    private fun updateCurrencyRates() {
        viewModelScope.launch {
            currenciesRepository.updateCurrencyRates()
        }
    }

    private fun loadAccounts() {
        val oldAccountsCount = _accounts.value.size
        viewModelScope.launch {
            _accounts.value  = accountsRepository.getAllAccountsFromDatabase()
            val newAccountsCount = _accounts.value.size
            if (oldAccountsCount in 1 until newAccountsCount) {
                _events.send(HomeEvent.ScrollToItemAccounts(newAccountsCount - 1))
            }
        }
    }

    private fun loadTotalAmount() {
        loadTotalAmountJob?.cancel()
        loadTotalAmountJob = currencyRatesFlow
            .onEach { currencyRates ->
                _totalAmount.value = accountsRepository.getAllAccountsFromDatabase()
                    .sumOf { account ->
                        currencyConverterInteractor.convertBalance(
                            balance = account.balance,
                            currencyRates = currencyRates,
                            fromCurrency = account.currency.name,
                            toCurrency = baseCurrencyName
                        )
                    }
            }
            .launchIn(viewModelScope + exceptionHandler)
    }
}