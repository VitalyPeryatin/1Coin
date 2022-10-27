package com.finance_tracker.finance_tracker.presentation.add_transaction.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance_tracker.finance_tracker.core.common.LocalContext
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import ru.alexgladkov.odyssey.compose.local.LocalRootController

enum class CategoryTab(val textId: String) {
    Income("add_transaction_tab_income"),
    Expense("add_transaction_tab_expense"),
    Transfer("add_transaction_tab_transfer"),
}

@Composable
fun CategoriesAppBar(
    modifier: Modifier = Modifier,
    selectedCategoryTab: CategoryTab = CategoryTab.Expense,
    onCategorySelect: (CategoryTab) -> Unit = {}
) {
    val rootController = LocalRootController.current
    TopAppBar(
        modifier = modifier
            .background(CoinTheme.color.background)
            .statusBarsPadding(),
        navigationIcon = {
            AppBarIcon(
                painter = rememberVectorPainter("ic_arrow_back"),
                onClick = { rootController.popBackStack() }
            )
        },
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 42.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                CategoryItem(
                    categoryTab = CategoryTab.Income,
                    selectedCategoryTab = selectedCategoryTab,
                    onClick = onCategorySelect
                )
                CategoryItem(
                    categoryTab = CategoryTab.Expense,
                    selectedCategoryTab = selectedCategoryTab,
                    onClick = onCategorySelect
                )
                CategoryItem(
                    categoryTab = CategoryTab.Transfer,
                    selectedCategoryTab = selectedCategoryTab,
                    onClick = onCategorySelect
                )
            }
        },
        actions = {
            AppBarIcon(painter = rememberVectorPainter("ic_arrow_refresh"))
        },
        backgroundColor = CoinTheme.color.background
    )
}

@Composable
private fun CategoryItem(
    categoryTab: CategoryTab,
    selectedCategoryTab: CategoryTab,
    modifier: Modifier = Modifier,
    onClick: (CategoryTab) -> Unit
) {
    val context = LocalContext.current
    Text(
        modifier = modifier
            .padding(horizontal = 1.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick.invoke(categoryTab) }
            .padding(horizontal = 7.dp, vertical = 8.dp),
        text = stringResource(categoryTab.textId),
        fontSize = 16.sp,
        color = if (selectedCategoryTab == categoryTab) {
            Color.Black
        } else {
            Color.Black.copy(alpha = 0.2f)
        }
    )
}