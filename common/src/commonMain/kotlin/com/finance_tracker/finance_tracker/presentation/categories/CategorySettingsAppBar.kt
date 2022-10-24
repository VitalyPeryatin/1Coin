package com.finance_tracker.finance_tracker.presentation.categories

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.finance_tracker.finance_tracker.core.common.LocalContext
import com.finance_tracker.finance_tracker.core.common.getLocalizedString
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.loadXmlPicture
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun CategorySettingsAppBar() {

    val rootController = LocalRootController.current
    val context = LocalContext.current

    TopAppBar(
        backgroundColor = CoinTheme.color.primaryVariant,
        navigationIcon = {
            AppBarIcon(
                painter = rememberVectorPainter(loadXmlPicture("ic_arrow_back")),
                onClick = { rootController.findRootController().popBackStack() },
            )
        },
        title = {
            Text(
                text = getLocalizedString(id = "category_settings", context = context),
                style = CoinTheme.typography.h4
            )
        },
        actions = {
            AppBarIcon(
                rememberVectorPainter(loadXmlPicture("ic_plus")),
                tint = CoinTheme.color.primary,
            )
        },
    )
}