package com.finance_tracker.finance_tracker.data.database

import com.finance_tracker.finance_tracker.AppDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

actual class DriverFactory {
  actual fun createDriver(): SqlDriver {
    return JdbcSqliteDriver("jdbc:sqlite:AppDatabase.db").also {
      AppDatabase.Schema.create(it)
    }
  }
}