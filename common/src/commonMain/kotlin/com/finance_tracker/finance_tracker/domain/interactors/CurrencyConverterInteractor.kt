package com.finance_tracker.finance_tracker.domain.interactors

import com.financetracker.financetracker.data.CurrencyRatesEntity

class CurrencyConverterInteractor {

    @Throws(IllegalStateException::class)
    fun convertBalance(
        balance: Double,
        currencyRates: Map<String, CurrencyRatesEntity>,
        fromCurrency: String,
        toCurrency: String
    ): Double {
        return if (fromCurrency == toCurrency) {
            balance
        } else {
            val fromCurrencyRates = currencyRates[fromCurrency]
                ?: return 0.0
            val toCurrencyRates = currencyRates[toCurrency]
                ?: return 0.0

            return balance / (fromCurrencyRates.rate * toCurrencyRates.rate)
        }
    }
}