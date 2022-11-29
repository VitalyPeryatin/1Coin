package com.finance_tracker.finance_tracker.presentation.settings.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter

@Composable
fun SettingSheetTelegramChatItem(
    modifier: Modifier = Modifier
) {

    val uriHandler = LocalUriHandler.current
    val uri = "https://t.me/+FFK1aCS6uJs1NTBi"

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                uriHandler.openUri(uri)
            }
            .padding(vertical = 12.dp),
    ) {
        Image(
            painter = rememberVectorPainter(id = "telegram"),
            contentDescription = null,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 8.dp
                )
                .size(24.dp)
                .align(Alignment.CenterVertically),
        )
        Text(
            text = stringResource("settings_telegram_community"),
            modifier = Modifier
                .padding(end = 8.dp)
                .align(Alignment.CenterVertically),
            style = CoinTheme.typography.body1
        )
    }
}