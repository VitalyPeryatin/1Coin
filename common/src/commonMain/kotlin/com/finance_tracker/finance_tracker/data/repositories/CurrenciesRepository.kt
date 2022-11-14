package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.data.network.CurrenciesNetworkDataSource
import com.financetracker.financetracker.CurrencyRatesEntity
import com.financetracker.financetracker.CurrencyRatesEntityQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonPrimitive

class CurrenciesRepository(
    private val currencyRatesEntityQueries: CurrencyRatesEntityQueries,
    private val currenciesNetworkDataSource: CurrenciesNetworkDataSource
) {

    fun getCurrencyRatesAsMap(): Flow<Map<String, CurrencyRatesEntity>> {
        return currencyRatesEntityQueries.getAllRates().asFlow()
                .mapToList()
                .map { currencyRatesEntities ->
                    currencyRatesEntities.associateBy { it.currency }
                }
                .flowOn(Dispatchers.IO)
    }

    suspend fun updateCurrencyRates() {
        return withContext(Dispatchers.IO) {
            val currencyRates = currenciesNetworkDataSource.getCurrenciesRates()
            currencyRatesEntityQueries.transaction {
                currencyRates.forEach { (key, value) ->
                    currencyRatesEntityQueries.insertNewRate(
                        currency = key,
                        rate = value.jsonPrimitive.double
                    )
                }
            }
        }
    }
}