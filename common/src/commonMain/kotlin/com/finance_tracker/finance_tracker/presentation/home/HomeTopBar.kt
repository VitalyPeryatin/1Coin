package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.DecimalFormatType
import com.finance_tracker.finance_tracker.core.common.DialogConfigurations
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.presentation.settings.SettingsSheet
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun HomeTopBar(
    totalAmount: Double,
    totalCurrency: Currency,
    modifier: Modifier = Modifier
) {
    val rootController = LocalRootController.current
    val modalController = rootController.findModalController()

    TopAppBar(
        modifier = modifier,
        backgroundColor = CoinTheme.color.primaryVariant,
        title = {
            Column {
                Text(
                    text = "${totalCurrency.sign}${DecimalFormatType.Amount.format(totalAmount)}",
                    style = CoinTheme.typography.h4
                )
                Text(
                    text = stringResource("home_topbar_text"),
                    style = CoinTheme.typography.subtitle2
                )
            }
        },
        actions = {
            Icon(
                painter = rememberVectorPainter("ic_settings"),
                contentDescription = null,
                Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(
                        color = CoinTheme.color.secondaryBackground,
                        shape = CircleShape
                    )
                    .clickable {
                        modalController.present(DialogConfigurations.bottomSheet) { key ->
                            SettingsSheet(
                                onCloseClick = { modalController.popBackStack(key) }
                            )
                        }
                    }
                    .padding(8.dp),
                tint = CoinTheme.color.content.copy(alpha = 0.8f)
            )
        },
    )
}