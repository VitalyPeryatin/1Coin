package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.data.database.mappers.categoryToDomainModel
import com.finance_tracker.finance_tracker.domain.models.Category
import com.financetracker.financetracker.CategoriesEntityQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoriesRepository(
    private val categoriesEntityQueries: CategoriesEntityQueries
) {
    suspend fun insertCategory(
        categoryName: String,
        categoryIcon: String,
    ) {
        withContext(Dispatchers.IO) {
            categoriesEntityQueries.insertCategory(
                id = null,
                name = categoryName,
                icon = categoryIcon,
            )
        }
    }

    suspend fun getAllCategories(): List<Category> {
        return withContext(Dispatchers.IO) {
            categoriesEntityQueries.getAllCategories().executeAsList()
                .map { it.categoryToDomainModel() }
        }
    }
}