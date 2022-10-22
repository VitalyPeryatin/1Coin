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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.LocalContext
import com.finance_tracker.finance_tracker.core.common.getLocalizedString
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.loadXmlPicture

@Composable
fun HomeTopBar() {
    val context = LocalContext.current
    TopAppBar(
        backgroundColor = CoinTheme.color.primaryVariant,
        title = {
            Column {
                Text(
                    text = "$42 520",
                    style = CoinTheme.typography.h4
                )
                Text(
                    text = getLocalizedString("home_topbar_text", context),
                    style = CoinTheme.typography.subtitle2
                )
            }
        },
        actions = {
            Icon(
                painter = rememberVectorPainter(loadXmlPicture("ic_settings")),
                contentDescription = null,
                Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(
                        color = CoinTheme.color.secondaryBackground,
                        shape = CircleShape
                    )
                    .clickable { }
                    .padding(8.dp),
                tint = CoinTheme.color.content.copy(alpha = 0.8f)
            )
        },
    )
}