package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinAlpha
import com.finance_tracker.finance_tracker.core.theme.CoinTheme

enum class CategoryTab(val textId: String) {
    Income("add_transaction_tab_income"),
    Expense("add_transaction_tab_expense"),
}

@Composable
fun ExpenseIncomeTabs(
    modifier: Modifier = Modifier,
    selectedCategoryTab: CategoryTab = CategoryTab.Expense,
    onCategorySelect: (CategoryTab) -> Unit = {}
) {
    Row(
        modifier = modifier
            .background(CoinTheme.color.background)
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
    onClick: (CategoryTab) -> Unit = {}
) {
    val targetTextColor = if (selectedCategoryTab == categoryTab) {
        Color.Black
    } else {
        Color.Black.copy(alpha = CoinAlpha.Medium)
    }
    val textColor by animateColorAsState(
        targetValue = targetTextColor,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
    )
    Text(
        modifier = modifier
            .padding(horizontal = 1.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick.invoke(categoryTab) }
            .padding(horizontal = 8.dp, vertical = 8.dp),
        text = stringResource(categoryTab.textId),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = textColor,
    )
}