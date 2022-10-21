package com.finance_tracker.finance_tracker.presentation.categories

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.finance_tracker.finance_tracker.R
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.loadXmlPicture
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Composable
fun CategorySettingsAppBar(
    navigator: DestinationsNavigator
) {

    TopAppBar(
        backgroundColor = CoinTheme.color.primaryVariant,
        navigationIcon = {
            AppBarIcon(
                painter = rememberVectorPainter(loadXmlPicture("ic_arrow_back")),
                onClick = { navigator.popBackStack() },
            )
        },
        title = {
            Text(
                text = stringResource(R.string.category_settings),
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

@Preview
@Composable
fun CategorySettingsAppBarPreview() {
    CategorySettingsAppBar(
        navigator = EmptyDestinationsNavigator
    )
}