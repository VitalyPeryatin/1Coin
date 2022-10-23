package com.finance_tracker.finance_tracker.data.database

import com.finance_tracker.finance_tracker.AppDatabase
import com.finance_tracker.finance_tracker.core.common.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(AppDatabase.Schema, context, "AppDatabase.db")
    }
}