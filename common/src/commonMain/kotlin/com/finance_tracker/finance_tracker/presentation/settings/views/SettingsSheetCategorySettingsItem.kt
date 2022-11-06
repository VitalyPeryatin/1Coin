package com.finance_tracker.finance_tracker.presentation.settings.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun SettingsSheetCategorySettingsItem(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit
) {

    val rootController = LocalRootController.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                rootController.findRootController().push(MainNavigationTree.CategorySettings.name)
                onCloseClick()
            }
            .padding(vertical = 12.dp),
    ) {
        Icon(
            painter = rememberVectorPainter(id = "ic_categories_settings"),
            contentDescription = null,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 8.dp
                )
                .size(24.dp)
                .align(Alignment.CenterVertically),
            tint = CoinTheme.color.content.copy(alpha = 0.6f)
        )
        Text(
            text = stringResource("settings_categories"),
            modifier = Modifier
                .align(Alignment.CenterVertically),
            style = CoinTheme.typography.body1
        )
    }
}