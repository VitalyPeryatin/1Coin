package com.finance_tracker.finance_tracker.di

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val coreModule = module {
    factoryOf(::provideJsonFactory)
    singleOf(::provideHttpClient)
}

private fun provideJsonFactory(): Json {
    return Json {
        prettyPrint = false
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
        encodeDefaults = true
    }
}

private fun provideHttpClient(jsonFactory: Json): HttpClient {
    return HttpClient {
        install(ContentNegotiation) {
            json(jsonFactory)
        }
        install(Logging) {
            logger = object: Logger {
                override fun log(message: String) {
                    Napier.v(message, tag = "HTTP-Client")
                }
            }
            level = LogLevel.ALL
        }
    }
}