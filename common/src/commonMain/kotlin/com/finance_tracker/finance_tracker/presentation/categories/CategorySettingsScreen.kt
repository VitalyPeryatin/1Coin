package com.finance_tracker.finance_tracker.presentation.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.LazyDragColumn
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.ui.CategoryCard
import com.finance_tracker.finance_tracker.core.ui.CategoryTab
import com.finance_tracker.finance_tracker.core.ui.ExpenseIncomeTabs
import com.finance_tracker.finance_tracker.core.ui.ItemWrapper
import com.finance_tracker.finance_tracker.domain.models.Category

@Composable
fun CategorySettingsScreen() {
    StoredViewModel<CategorySettingsViewModel> { viewModel ->

        LaunchedEffect(Unit) {
            viewModel.onScreenComposed()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {

            val incomeCategories by viewModel.incomeCategories.collectAsState()
            val expenseCategories by viewModel.expenseCategories.collectAsState()
            val selectedCategoryTab by viewModel.chosenScreen.collectAsState()

            CategorySettingsAppBar(selectedCategoryTab = selectedCategoryTab)

            ExpenseIncomeTabs(
                modifier = Modifier
                    .padding(
                        top = 24.dp,
                        start = 20.dp,
                        bottom = 4.dp,
                    ),
                onCategorySelect = viewModel::onCategorySelect
            )

            when (selectedCategoryTab) {
                CategoryTab.Expense -> {
                    CategoriesLazyColumn(
                        categories = expenseCategories,
                        onSwap = viewModel::swapExpenseCategories,
                        onCrossDeleteClick = viewModel::deleteCategory
                    )
                }

                CategoryTab.Income -> {
                    CategoriesLazyColumn(
                        categories = incomeCategories,
                        onSwap = viewModel::swapIncomeCategories,
                        onCrossDeleteClick = viewModel::deleteCategory
                    )
                }
            }
        }
    }
}

@Composable
fun CategoriesLazyColumn(
    categories: List<Category>,
    onSwap: (Int, Int) -> Unit,
    onCrossDeleteClick: (Long) -> Unit,
) {
    LazyDragColumn(
        items = categories,
        onSwap = onSwap,
        contentPaddingValues = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp,
        )
    ) { index, category ->
        ItemWrapper(
            isFirstItem = index == 0,
            isLastItem = index == categories.lastIndex,
        ) {
            CategoryCard(
                data = category,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 8.dp,
                        bottom = 8.dp,
                        end = 16.dp
                    ),
                onCrossDeleteClick = {
                    onCrossDeleteClick.invoke(category.id)
                }
            )
        }
    }
}
