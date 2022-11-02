package com.finance_tracker.finance_tracker.presentation.add_category

import com.adeo.kviewmodel.KViewModel
import com.finance_tracker.finance_tracker.data.repositories.CategoriesRepository
import com.finance_tracker.finance_tracker.domain.models.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddCategoryViewModel(
    private val repository: CategoriesRepository
): KViewModel() {

    private val _chosenCategory = MutableStateFlow(
        Category(
            id = 0,
            name = "Restaurant",
            iconId = "ic_category_1"
        )
    )
    val chosenCategory = _chosenCategory.asStateFlow()

    private val _newCategoryName = MutableStateFlow("")
    val newCategoryName = _newCategoryName.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    init {
        loadAllCategories()
    }

    private fun loadAllCategories() {
        viewModelScope.launch {
            _categories.value = repository.getAllDefaultCategories()
        }
    }

    fun onCategoryChoose(category: Category) {
        _chosenCategory.value = category
    }

    fun addExpenseCategory(categoryName: String, categoryIcon: String) {
        viewModelScope.launch {
            repository.insertCategory(
                categoryName = categoryName,
                categoryIcon = categoryIcon,
                isExpense = true,
                isIncome = false
            )
        }
    }

    fun addIncomeCategory(categoryName: String, categoryIcon: String) {
        viewModelScope.launch {
            repository.insertCategory(
                categoryName = categoryName,
                categoryIcon = categoryIcon,
                isExpense = false,
                isIncome = true
            )
        }
    }

    fun setCategoryName(name: String) {
        _newCategoryName.value = name
    }
}