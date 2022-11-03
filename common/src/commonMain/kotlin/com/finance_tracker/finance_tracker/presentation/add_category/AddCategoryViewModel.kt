package com.finance_tracker.finance_tracker.presentation.add_category

import com.adeo.kviewmodel.KViewModel
import com.finance_tracker.finance_tracker.data.repositories.CategoriesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddCategoryViewModel(
    private val repository: CategoriesRepository
): KViewModel() {

    private val _icons = MutableStateFlow(List(63) { "ic_category_${it + 1}" })
    val icons = _icons.asStateFlow()

    private val _chosenIcon = MutableStateFlow(icons.value.first())
    val chosenIcon = _chosenIcon.asStateFlow()

    private val _categoryName = MutableStateFlow("")
    val categoryName = _categoryName.asStateFlow()

    fun onIconChoose(icon: String) {
        _chosenIcon.value = icon
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
        _categoryName.value = name
    }
}