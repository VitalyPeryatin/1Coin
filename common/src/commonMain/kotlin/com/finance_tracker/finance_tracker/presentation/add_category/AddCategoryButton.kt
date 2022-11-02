package com.finance_tracker.finance_tracker.presentation.add_category

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.CoinDropdownMenu
import com.finance_tracker.finance_tracker.core.ui.DropdownMenuItem
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.Category

@Composable
fun AddCategoryButton(
    chosenCategory: Category,
    categories: List<Category>,
    onCategoryChoose: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
    ) {
        IconButton(
            modifier = Modifier
                .size(48.dp)
                .border(
                    width = 1.dp,
                    color = CoinTheme.color.content.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(12.dp)
                ),
            onClick = { expanded = true },
        ) {
            Icon(
                painter = rememberVectorPainter(chosenCategory.iconId),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
                    .align(Alignment.Center),
            )
        }

        Box {
            CoinDropdownMenu(
                modifier = Modifier
                    .width(200.dp)
                    .requiredSizeIn(maxHeight = 180.dp),
                expanded = expanded,
                onDismissRequest = { expanded = false },
                xOffset = 48.dp,
                yOffset = 48.dp,
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onCategoryChoose.invoke(category)
                        },
                        modifier = Modifier
                    ) {
                        Icon(
                            painter = rememberVectorPainter(category.iconId),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 8.dp)
                        )
                        Text(
                            text = category.name,
                        )
                    }
                }
            }
        }
    }
}
