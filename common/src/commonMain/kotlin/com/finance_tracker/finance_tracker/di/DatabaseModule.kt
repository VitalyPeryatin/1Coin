package com.finance_tracker.finance_tracker.di

import com.finance_tracker.finance_tracker.AppDatabase
import com.finance_tracker.finance_tracker.data.database.DatabaseInitializer
import com.finance_tracker.finance_tracker.data.database.DriverFactory
import com.financetracker.financetracker.AccountsEntity
import com.financetracker.financetracker.TransactionsEntity
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.EnumColumnAdapter
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import java.text.SimpleDateFormat
import java.util.*

internal val databaseModule = module {
    singleOf(::DatabaseInitializer)
    singleOf(::provideAppDatabase)
    factory { get<AppDatabase>().transactionsEntityQueries }
    factory { get<AppDatabase>().smsMessageEntityQueries }
    factory { get<AppDatabase>().accountsEntityQueries }
    factory { get<AppDatabase>().categoriesEntityQueries }
    factory { get<AppDatabase>().accountColorsEntityQueries }
    factory { get<AppDatabase>().defaultCategoriesEntityQueries }
}

private fun provideAppDatabase(driverFactory: DriverFactory): AppDatabase {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
    val dateAdapter = object : ColumnAdapter<Date, String> {
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