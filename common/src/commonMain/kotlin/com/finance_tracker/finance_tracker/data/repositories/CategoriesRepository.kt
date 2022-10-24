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
                position = null,
            )
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

    suspend fun updateCategoryPosition(categoryFrom: Category, categoryTo: Category) {
        withContext(Dispatchers.IO) {
            categoriesEntityQueries.replaceCategory(categoryFrom.id, categoryTo.id)
            categoriesEntityQueries.replaceCategory(categoryTo.id, categoryFrom.id)
        }
    }
}