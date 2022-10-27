package com.finance_tracker.finance_tracker.presentation.categories

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun CategorySettingsAppBar() {
    val rootController = LocalRootController.current
    TopAppBar(
        backgroundColor = CoinTheme.color.primaryVariant,
        navigationIcon = {
            AppBarIcon(
                painter = rememberVectorPainter("ic_arrow_back"),
                onClick = { rootController.findRootController().popBackStack() },
            )
        },
        title = {
            Text(
                text = stringResource("category_settings"),
                style = CoinTheme.typography.h4
            )
        },
        actions = {
            AppBarIcon(
                rememberVectorPainter("ic_plus"),
                tint = CoinTheme.color.primary,
            )
        },
    )
}