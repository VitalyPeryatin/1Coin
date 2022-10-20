package com.finance_tracker.finance_tracker.presentation.categories

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.R
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

enum class CategoryTab(@StringRes val textRes: Int) {
    Income(R.string.add_transaction_tab_income),
    Expense(R.string.add_transaction_tab_expense),
}

@Composable
fun CategorySettingsTabs(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    selectedCategoryTab: CategoryTab = CategoryTab.Expense,
    onCategorySelect: (CategoryTab) -> Unit = {}
) {

    Row(
        modifier = modifier
            .background(CoinTheme.color.background)
            .statusBarsPadding(),
    ) {
        CategoryItem(
            categoryTab = CategoryTab.Expense,
            selectedCategoryTab = selectedCategoryTab,
            onClick = onCategorySelect
        )
        CategoryItem(
            categoryTab = CategoryTab.Income,
            selectedCategoryTab = selectedCategoryTab,
            onClick = onCategorySelect
        )
    }
}

@Composable
private fun CategoryItem(
    categoryTab: CategoryTab,
    selectedCategoryTab: CategoryTab,
    modifier: Modifier = Modifier,
    onClick: (CategoryTab) -> Unit
) {
    Text(
        modifier = modifier
            .padding(horizontal = 1.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick.invoke(categoryTab) }
            .padding(horizontal = 7.dp, vertical = 8.dp),
        text = stringResource(categoryTab.textRes),
        style = CoinTheme.typography.h5,
        color = if (selectedCategoryTab == categoryTab) {
            Color.Black
        } else {
            Color.Black.copy(alpha = 0.2f)
        }
    )
}

@Preview
@Composable
fun CategorySettingsTabsPreview() {
    CategorySettingsTabs(navigator = EmptyDestinationsNavigator)
}