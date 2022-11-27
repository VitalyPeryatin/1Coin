package com.finance_tracker.finance_tracker.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.presentation.settings.views.SettingSheetTelegramChatItem
import com.finance_tracker.finance_tracker.presentation.settings.views.SettingsSheetCategorySettingsItem
import com.finance_tracker.finance_tracker.presentation.settings.views.SettingsSheetMainCurrencyItem

@Composable
fun SettingsSheet(onCloseClick: () -> Unit) {
    StoredViewModel<SettingsSheetViewModel> { viewModel ->
        val chosenCurrency by viewModel.chosenCurrency.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding(),
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        vertical = 16.dp,
                        horizontal = 12.dp
                    )
                    .align(Alignment.CenterHorizontally),
                text = stringResource("settings_top_text"),
                style = CoinTheme.typography.h4
            )
            Divider(
                color = CoinTheme.color.dividers,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
            SettingsSheetMainCurrencyItem(
                selectedCurrency = chosenCurrency,
                onCurrencySelect = viewModel::onCurrencySelect
            )
            SettingsSheetCategorySettingsItem {
                onCloseClick()
            }
            SettingSheetTelegramChatItem()
        }
    }
}