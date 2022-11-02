package com.finance_tracker.finance_tracker.data.database.mappers

import com.finance_tracker.finance_tracker.domain.models.Category
import com.financetracker.financetracker.DefaultCategoriesEntity

fun DefaultCategoriesEntity.categoryToDomainModel(): Category {
    return Category(
        id = id,
        name = name,
        iconId = icon
    )
}