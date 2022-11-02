package com.finance_tracker.finance_tracker.presentation.add_category

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun AddCategoryAppBar(
    textValue: String,
) {

    val rootController = LocalRootController.current
    
    TopAppBar(
        backgroundColor = CoinTheme.color.primaryVariant,
        navigationIcon = {
            AppBarIcon(
                painter = rememberVectorPainter("ic_arrow_back"),
                onClick = { rootController.popBackStack() },
            )
        },
        title = {
            Text(
                text = stringResource(textValue),
                style = CoinTheme.typography.h4
            )
        },
    )
}