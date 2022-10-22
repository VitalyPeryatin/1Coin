package com.finance_tracker.finance_tracker.presentation.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.LocalContext
import com.finance_tracker.finance_tracker.core.common.getLocalizedString
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.loadXmlPicture

@Composable
fun MyAccountsHeader(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Row(
        modifier = modifier
            .padding(
                start = 16.dp,
                end = 16.dp
            )
    ) {
        Text(
            text = getLocalizedString("home_my_accounts", context),
            style = CoinTheme.typography.h4
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = rememberVectorPainter(loadXmlPicture("ic_arrow_next_small")),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterVertically)
        )
    }
}