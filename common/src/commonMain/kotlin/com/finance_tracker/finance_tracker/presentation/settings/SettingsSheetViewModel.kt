package com.finance_tracker.finance_tracker.presentation.settings

import com.adeo.kviewmodel.KViewModel
import com.finance_tracker.finance_tracker.domain.models.Currency
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsSheetViewModel(): KViewModel() {

    private val _chosenCurrency = MutableStateFlow(Currency.list.first())
    val chosenCurrency = _chosenCurrency.asStateFlow()

    fun onCurrencySelect(currency: Currency) {
        _chosenCurrency.value = currency
    }
}