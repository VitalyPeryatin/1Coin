package com.finance_tracker.finance_tracker.core.ui.tab_rows

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.theme.staticTextSize

@Composable
fun CoinTabRow(
    selectedTabIndex: Int,
    data: List<String>,
    modifier: Modifier = Modifier,
    onTabSelect: (index: Int) -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        ScrollableTabRow(
            edgePadding = 0.dp,
            selectedTabIndex = selectedTabIndex,
            backgroundColor = CoinTheme.color.background,
            contentColor = CoinTheme.color.primary,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .padding(horizontal = 16.dp),
                    height = 2.dp
                )
            },
            divider = {
                TabRowDefaults.Divider(thickness = 0.dp)
            },
            tabs = {
                data.forEachIndexed { index, tab ->
                    CoinTab(
                        text = data[index],
                        selected = selectedTabIndex == index,
                        onClick = { onTabSelect.invoke(index) }
                    )
                }
            }
        )

        TabRowDefaults.Divider(
            color = CoinTheme.color.dividers
        )
    }
}

@Composable
private fun CoinTab(
    text: String,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {}
) {
    Tab(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        text = {
            Text(
                text = text,
                style = CoinTheme.typography.body2_medium.staticTextSize(),
                color = if (selected) {
                    CoinTheme.color.primary
                } else {
                    CoinTheme.color.secondary
                }
            )
        }
    )
}