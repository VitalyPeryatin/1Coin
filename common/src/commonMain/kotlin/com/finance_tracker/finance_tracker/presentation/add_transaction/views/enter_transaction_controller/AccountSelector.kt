package com.finance_tracker.finance_tracker.presentation.add_transaction.views.enter_transaction_controller

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.loadXmlPicture
import com.finance_tracker.finance_tracker.domain.models.Account
import kotlin.math.roundToInt

@Composable
fun AccountSelector(
    accounts: List<Account>,
    onAccountSelect: (Account) -> Unit,
    onAccountAdd: () -> Unit
) {
    var gridWidth by remember { mutableStateOf(1) }
    var cellWidth by remember { mutableStateOf(1) }
    val columnCount by remember {
        derivedStateOf {
            (gridWidth.toFloat() / cellWidth)
                .roundToInt()
                .coerceAtLeast(1)
        }
    }

    val dividerWidth = 0.5.dp
    val dividerColor = CoinTheme.color.dividers
    Divider(
        modifier = Modifier
            .background(dividerColor)
            .height(dividerWidth)
            .fillMaxWidth()
    )
    val cellHeight = 68.dp
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxHeight()
            .onSizeChanged {
                gridWidth = it.width
            },
        columns = GridCells.Adaptive(156.dp)
    ) {
        itemsIndexed(accounts) { index, account ->
            BoxWithDividers(
                modifier = Modifier.clickable {
                    onAccountSelect.invoke(account)
                },
                columnCount = columnCount,
                index = index,
                dividerWidth = dividerWidth,
                dividerColor = dividerColor
            ) {
                AccountCard(
                    modifier = Modifier
                        .height(cellHeight)
                        .fillMaxSize(),
                    account = account
                )
            }
        }
        item {
            BoxWithDividers(
                modifier = Modifier.clickable {
                    onAccountAdd.invoke()
                },
                columnCount = columnCount,
                index = accounts.size,
                dividerWidth = dividerWidth,
                dividerColor = dividerColor
            ) {
                Box(
                    modifier = Modifier
                        .onSizeChanged {
                            cellWidth = it.width
                        }
                        .height(cellHeight)
                        .fillMaxSize()
                ) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center),
                        painter = rememberVectorPainter(loadXmlPicture("ic_plus")),
                        tint = CoinTheme.color.secondary,
                        contentDescription = null
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun AccountSelectorPreview() {
    AccountSelector(
        accounts = emptyList(),
        onAccountSelect = {},
        onAccountAdd = {}
    )
}