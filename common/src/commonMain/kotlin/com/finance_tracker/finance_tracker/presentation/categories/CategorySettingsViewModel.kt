package com.finance_tracker.finance_tracker.presentation.categories

import com.adeo.kviewmodel.KViewModel
import com.finance_tracker.finance_tracker.core.ui.CategoryTab
import com.finance_tracker.finance_tracker.data.repositories.CategoriesRepository
import com.finance_tracker.finance_tracker.domain.models.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategorySettingsViewModel(
    private val repository: CategoriesRepository
): KViewModel() {

    private val _expenseCategories = MutableStateFlow<List<Category>>(emptyList())
    val expenseCategories = _expenseCategories.asStateFlow()

    private val _incomeCategories = MutableStateFlow<List<Category>>(emptyList())
    val incomeCategories = _incomeCategories.asStateFlow()

    private val _chosenScreen = MutableStateFlow(CategoryTab.Expense)
    val chosenScreen = _chosenScreen.asStateFlow()

    fun onCategorySelect(categoryTab: CategoryTab) {
        _chosenScreen.value = categoryTab
    }

    fun onScreenComposed() {
        loadAllCategories()
    }

    private fun loadAllCategories() {
        loadAllExpenseCategories()
        loadAllIncomeCategories()
    }

    private fun loadAllExpenseCategories() {
        viewModelScope.launch {
            _expenseCategories.value = repository.getAllExpenseCategories()
        }
    }

    private fun loadAllIncomeCategories() {
        viewModelScope.launch {
            _incomeCategories.value = repository.getAllIncomeCategories()
        }
    }

    fun swapExpenseCategories(from: Int, to: Int) {
        val fromItem = _expenseCategories.value[from]
        val toItem = _expenseCategories.value[to]
        val newList = _expenseCategories.value.toMutableList()
        newList[from] = toItem
        newList[to] = fromItem

        _expenseCategories.value = newList
        saveListState(fromItem.id, toItem.id)
    }

    fun swapIncomeCategories(from: Int, to: Int) {
        val fromItem = _incomeCategories.value[from]
        val toItem = _incomeCategories.value[to]
        val newList = _incomeCategories.value.toMutableList()

        newList[from] = toItem
        newList[to] = fromItem

        _incomeCategories.value = newList
        saveListState(fromItem.id, toItem.id)
    }

    private fun saveListState(categoryFromId: Long, categoryToId: Long) {
        viewModelScope.launch {
            repository.updateCategoryPosition(categoryFromId, categoryToId)
        }
    }

    fun deleteCategory(id: Long) {
        viewModelScope.launch {
            repository.deleteCategoryById(id)

            if(_chosenScreen.value == CategoryTab.Expense) {
                loadAllExpenseCategories()
            } else {
                loadAllIncomeCategories()
            }
        }
    }
}