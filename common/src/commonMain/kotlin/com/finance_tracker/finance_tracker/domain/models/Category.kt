package com.finance_tracker.finance_tracker.domain.models

data class Category(
    val id: Long,
    val name: String,
    val iconId: String
) {
    companion object {
        val EMPTY = Category(
            id = -1,
            name = "Correct",
            iconId = "ic_category_13"
        )
    }
}