package com.finance_tracker.finance_tracker.presentation.tabs_navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import ru.alexgladkov.odyssey.compose.controllers.MultiStackRootController
import ru.alexgladkov.odyssey.compose.controllers.TabNavigationModel
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun BottomNavigationBar(
    selectedTabItem: TabNavigationModel,
    modifier: Modifier = Modifier
) {
    val rootController = LocalRootController.current as MultiStackRootController
    val tabItems = rootController.tabItems.take(2) + null + rootController.tabItems.takeLast(2)
    BottomAppBar(
        modifier = modifier,
        backgroundColor = CoinTheme.color.background,
        contentColor = CoinTheme.color.primary,
        cutoutShape = CircleShape
    ) {
        tabItems.forEach { item ->
            if (item != null) {
                BottomNavigationItem(
                    item = item,
                    isSelected = selectedTabItem == item
                )
            } else {
                EmptyBottomNavigationItem()
            }
        }
    }
}

@Composable
private fun EmptyBottomNavigationItem() {
    Box(modifier = Modifier.width(48.dp))
}

@Composable
private fun RowScope.BottomNavigationItem(
    item: TabNavigationModel,
    isSelected: Boolean
) {
    val rootController = LocalRootController.current as MultiStackRootController
    val itemConfiguration = item.tabInfo.tabItem.configuration
    BottomNavigationItem(
        icon = {
            Icon(
                painter = itemConfiguration.selectedIcon!!,
                contentDescription = itemConfiguration.title
            )
        },
        label = {
            Text(
                text = itemConfiguration.title,
                style = CoinTheme.typography.subtitle5,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        selectedContentColor = CoinTheme.color.primary,
        unselectedContentColor = CoinTheme.color.secondary,
        alwaysShowLabel = true,
        selected = isSelected,
        onClick = {
            rootController.switchTab(item)
        }
    )
}