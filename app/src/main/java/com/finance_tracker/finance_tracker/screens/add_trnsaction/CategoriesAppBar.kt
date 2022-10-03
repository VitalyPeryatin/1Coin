package com.finance_tracker.finance_tracker.screens.add_trnsaction

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance_tracker.finance_tracker.R
import com.finance_tracker.finance_tracker.core_ui.AppBarIcon
import com.finance_tracker.finance_tracker.theme.CoinTheme

enum class Category(@StringRes val textRes: Int) {
    Income(R.string.add_transaction_tab_income),
    Expense(R.string.add_transaction_tab_expense),
    Transfer(R.string.add_transaction_tab_transfer),
}

@Composable
fun CategoriesAppBar(
    modifier: Modifier = Modifier,
    selectedCategory: Category = Category.Expense,
    onCategorySelect: (Category) -> Unit = {}
) {
    TopAppBar(
        modifier = modifier
            .background(CoinTheme.color.background)
            .statusBarsPadding(),
        navigationIcon = {
            AppBarIcon(painter = painterResource(R.drawable.ic_arrow_back))
        },
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 42.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                CategoryItem(
                    category = Category.Income,
                    selectedCategory = selectedCategory,
                    onClick = onCategorySelect
                )
                CategoryItem(
                    category = Category.Expense,
                    selectedCategory = selectedCategory,
                    onClick = onCategorySelect
                )
                CategoryItem(
                    category = Category.Transfer,
                    selectedCategory = selectedCategory,
                    onClick = onCategorySelect
                )
            }
        },
        actions = {
            AppBarIcon(painter = painterResource(R.drawable.ic_more_vert))
        },
        backgroundColor = Color.White
    )
}

@Composable
private fun CategoryItem(
    category: Category,
    selectedCategory: Category,
    modifier: Modifier = Modifier,
    onClick: (Category) -> Unit
) {
    Text(
        modifier = modifier
            .padding(horizontal = 1.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick.invoke(category) }
            .padding(horizontal = 7.dp, vertical = 8.dp),
        text = stringResource(category.textRes),
        fontSize = 16.sp,
        color = if (selectedCategory == category) {
            Color.Black
        } else {
            Color.Black.copy(alpha = 0.2f)
        }
    )
}

@Preview
@Composable
fun CategoriesAppBarPreview() {
    CategoriesAppBar()
}