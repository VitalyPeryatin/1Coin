package com.finance_tracker.finance_tracker.data.database.di

import com.finance_tracker.finance_tracker.AppDatabase
import com.finance_tracker.finance_tracker.data.database.DatabaseInitializer
import com.finance_tracker.finance_tracker.data.database.DriverFactory
import com.financetracker.financetracker.data.AccountsEntity
import com.financetracker.financetracker.data.TransactionsEntity
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.EnumColumnAdapter
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import java.text.SimpleDateFormat
import java.util.Date

internal val coreDatabaseModule = module {
    singleOf(::DatabaseInitializer)
    singleOf(::provideAppDatabase)
    factory { get<AppDatabase>().transactionsEntityQueries }
    factory { get<AppDatabase>().smsMessageEntityQueries }
    factory { get<AppDatabase>().accountsEntityQueries }
    factory { get<AppDatabase>().categoriesEntityQueries }
    factory { get<AppDatabase>().currencyRatesEntityQueries }
}

private fun provideAppDatabase(driverFactory: DriverFactory): AppDatabase {

    val dateAdapter = object : ColumnAdapter<Date, String> {

        private val threadLocalFormatter = ThreadLocal<SimpleDateFormat>()
        private val formatter: SimpleDateFormat
            get() {
                val formatter = threadLocalFormatter.get()
                if (formatter == null) {
                    val newFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                    threadLocalFormatter.set(newFormatter)
                    return newFormatter
                }
                return formatter
            }

        override fun decode(databaseValue: String) = formatter.parse(databaseValue)!!

        override fun encode(value: Date) = formatter.format(value)
    }

    val driver = driverFactory.createDriver()
    return AppDatabase(
        driver = driver,
        TransactionsEntityAdapter = TransactionsEntity.Adapter(
            typeAdapter = EnumColumnAdapter(),
            insertionDateAdapter = dateAdapter,
            dateAdapter = dateAdapter
        ),
        AccountsEntityAdapter = AccountsEntity.Adapter(
            typeAdapter = EnumColumnAdapter(),
        )
    )
}