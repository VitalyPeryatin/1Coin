package com.finance_tracker.finance_tracker.data.database

import com.finance_tracker.finance_tracker.core.common.Context
import com.finance_tracker.finance_tracker.core.common.getRaw
import com.finance_tracker.finance_tracker.domain.models.Category
import com.financetracker.financetracker.CategoriesEntityQueries
import com.financetracker.financetracker.CurrencyRatesEntityQueries
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonPrimitive

class DatabaseInitializer(
    private val jsonFactory: Json,
    private val categoriesEntityQueries: CategoriesEntityQueries,
    private val currencyRatesEntityQueries: CurrencyRatesEntityQueries
) {

    private val defaultExpenseCategories = listOf(
        Category(
            id = 0,
            name = "Restoraunt",
            iconId = "ic_category_1"
        ),
        Category(
            id = 1,
            name = "Health",
            iconId = "ic_category_2"
        ),
        Category(
            id = 2,
            name = "Child",
            iconId = "ic_category_3"
        ),
        Category(
            id = 3,
            name = "Car",
            iconId = "ic_category_4"
        ),
        Category(
            id = 4,
            name = "Education",
            iconId = "ic_category_5"
        ),
        Category(
            id = 5,
            name = "Entertainment",
            iconId = "ic_category_6"
        ),
        Category(
            id = 6,
            name = "Sport",
            iconId = "ic_category_7"
        ),
        Category(
            id = 7,
            name = "Public Transport",
            iconId = "ic_category_8"
        ),
        Category(
            id = 8,
            name = "Shop",
            iconId = "ic_category_9"
        ),
        Category(
            id = 9,
            name = "Utilities",
            iconId = "ic_category_10"
        ),
        Category(
            id = 10,
            name = "Clothes",
            iconId = "ic_category_11"
        ),
        Category(
            id = 11,
            name = "Electronics",
            iconId = "ic_category_12"
        ),
        Category(
            id = 12,
            name = "Correct",
            iconId = "ic_category_13"
        )
    )

    private val defaultIncomeCategories = listOf(
        Category(
            id = 13,
            name = "Salary",
            iconId = "ic_category_14"
        ),
        Category(
            id = 14,
            name = "Allowance",
            iconId = "ic_category_15"
        ),
        Category(
            id = 15,
            name = "Gift",
            iconId = "ic_category_16"
        )
    )


    fun init(context: Context) {
        initCategories()
        initCurrencyRates(context)
    }

    private fun initCategories() {
        categoriesEntityQueries.transaction {
            val databaseCategories = categoriesEntityQueries.getAllCategories().executeAsList()

            if (databaseCategories.isEmpty()) {
                defaultExpenseCategories.forEach { category ->
                    categoriesEntityQueries.insertCategory(
                        id = category.id,
                        name = category.name,
                        icon = category.iconId,
                        position = category.id,
                        isExpense = true,
                        isIncome = false,
                    )
                }
                defaultIncomeCategories.forEach { category ->
                    categoriesEntityQueries.insertCategory(
                        id = category.id,
                        name = category.name,
                        icon = category.iconId,
                        position = category.id,
                        isExpense = false,
                        isIncome = true,
                    )
                }
            }
        }
    }

    private fun initCurrencyRates(context: Context) {
        val currencyRatesJson = getRaw(context, "base_rates", "json")
        currencyRatesEntityQueries.transaction {
            val databaseCurrencyRates = currencyRatesEntityQueries.getAllRates().executeAsList()

            if (databaseCurrencyRates.isEmpty()) {
                val currencyRatesObject = jsonFactory.decodeFromString<JsonObject>(currencyRatesJson)
                currencyRatesObject.forEach { (key, value) ->
                    currencyRatesEntityQueries.insertNewRate(
                        currency = key,
                        rate = value.jsonPrimitive.double
                    )
                }
            }
        }
    }
}