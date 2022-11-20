package com.finance_tracker.finance_tracker.presentation.add_transaction.views

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import ru.alexgladkov.odyssey.compose.local.LocalRootController

enum class CategoryTab(val textId: String) {
    Income("add_transaction_tab_income"),
    Expense("add_transaction_tab_expense")
}

private fun TransactionType.toCategoryTab(): CategoryTab {
    return when (this) {
        TransactionType.Expense -> CategoryTab.Expense
        TransactionType.Income -> CategoryTab.Income
    }
}

private fun CategoryTab.toTransactionType(): TransactionType {
    return when (this) {
        CategoryTab.Expense -> TransactionType.Expense
        CategoryTab.Income -> TransactionType.Income
    }
}

@Composable
fun CategoriesAppBar(
    doneButtonEnabled: Boolean,
    modifier: Modifier = Modifier,
    selectedTransactionType: TransactionType = TransactionType.Expense,
    onTransactionTypeSelect: (TransactionType) -> Unit = {},
    onDoneClick: () -> Unit = {}
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
                    selectedCategoryTab = selectedTransactionType.toCategoryTab(),
                    onClick = { onTransactionTypeSelect.invoke(it.toTransactionType()) }
                )
                CategoryItem(
                    categoryTab = CategoryTab.Expense,
                    selectedCategoryTab = selectedTransactionType.toCategoryTab(),
                    onClick = { onTransactionTypeSelect.invoke(it.toTransactionType()) }
                )
            }
        },
        actions = {
            AppBarIcon(
                painter = rememberVectorPainter("ic_done"),
                onClick = onDoneClick,
                enabled = doneButtonEnabled
            )
        },
        backgroundColor = CoinTheme.color.background
    )
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
        Color.Black.copy(alpha = 0.2f)
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
            .padding(horizontal = 7.dp, vertical = 8.dp),
        text = stringResource(categoryTab.textId),
        fontSize = 16.sp,
        color = textColor
    )
}