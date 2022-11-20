package com.finance_tracker.finance_tracker.data.network

import com.finance_tracker.finance_tracker.core.common.getOrThrow
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

class CurrenciesNetworkDataSource(
    private val httpClient: HttpClient,
    private val jsonFactory: Json
) {
    suspend fun getCurrenciesRates(): JsonObject {
        val jsonBody = httpClient.get("https://api.exchangerate.host/latest?base=USD").bodyAsText()
        return jsonFactory.decodeFromString<JsonObject>(jsonBody)
            .getOrThrow("rates").jsonObject
    }
}