package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.data.database.mappers.categoryToDomainModel
import com.finance_tracker.finance_tracker.domain.models.Category
import com.financetracker.financetracker.CategoriesEntityQueries
import com.financetracker.financetracker.DefaultCategoriesEntityQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoriesRepository(
    private val categoriesEntityQueries: CategoriesEntityQueries,
    private val defaultCategoriesEntityQueries: DefaultCategoriesEntityQueries
) {
    suspend fun insertCategory(
        categoryName: String,
        categoryIcon: String,
        isExpense: Boolean,
        isIncome: Boolean,
    ) {
        withContext(Dispatchers.IO) {
            categoriesEntityQueries.insertCategory(
                id = null,
                name = categoryName,
                icon = categoryIcon,
                position = null,
                isExpense = isExpense,
                isIncome = isIncome,
            )
        }
    }

    suspend fun getAllDefaultCategories(): List<Category> {
        return withContext(Dispatchers.IO) {
            defaultCategoriesEntityQueries.getAllCategories().executeAsList()
                .map { it.categoryToDomainModel() }
        }
    }

    suspend fun getAllCategories(): List<Category> {
        return withContext(Dispatchers.IO) {
            categoriesEntityQueries.getAllCategories().executeAsList()
                .map { it.categoryToDomainModel() }
        }
    }

    suspend fun deleteCategoryById(id: Long) {
        withContext(Dispatchers.IO) {
            categoriesEntityQueries.deleteCategoryById(id)
        }
    }

    suspend fun updateCategoryPosition(categoryFrom: Long, categoryTo: Long) {
        withContext(Dispatchers.IO) {
            categoriesEntityQueries.replaceCategory(categoryFrom, categoryTo)
            categoriesEntityQueries.replaceCategory(categoryTo, categoryFrom)
        }
    }

    suspend fun getAllExpenseCategories(): List<Category> {
        return withContext(Dispatchers.IO) {
            categoriesEntityQueries.getAllExpenseCategories().executeAsList()
                .map { it.categoryToDomainModel() }
        }
    }

    suspend fun getAllIncomeCategories(): List<Category> {
        return withContext(Dispatchers.IO) {
            categoriesEntityQueries.getAllIncomeCategories().executeAsList()
                .map { it.categoryToDomainModel() }
        }
    }
}