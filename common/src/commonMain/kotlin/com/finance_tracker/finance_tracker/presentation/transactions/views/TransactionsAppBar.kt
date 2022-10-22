package com.finance_tracker.finance_tracker.presentation.transactions.views

import androidx.compose.foundation.background
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.finance_tracker.finance_tracker.core.common.LocalContext
import com.finance_tracker.finance_tracker.core.common.getLocalizedString
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.loadXmlPicture

@Composable
fun TransactionsAppBar(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    TopAppBar(
        modifier = modifier
            .background(CoinTheme.color.background)
            .statusBarsPadding(),
        title = {
            Text(
                text = getLocalizedString("transactions_title", context),
                style = CoinTheme.typography.h4
            )
        },
        actions = {
            AppBarIcon(painter = rememberVectorPainter(loadXmlPicture("ic_more_vert")))
        },
        backgroundColor = Color.White
    )
}