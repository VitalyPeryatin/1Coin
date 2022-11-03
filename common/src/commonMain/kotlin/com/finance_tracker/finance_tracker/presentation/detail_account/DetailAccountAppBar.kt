package com.finance_tracker.finance_tracker.presentation.detail_account

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.AppBarIcon
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun DetailAccountAppBar(
    name: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    val navController = LocalRootController.current.findRootController()
    TopAppBar(
        modifier = modifier
            .background(color)
            .statusBarsPadding(),
        title = {
            Text(
                text = name,
                style = CoinTheme.typography.h5,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = CoinTheme.color.primaryVariant
            )
        },
        navigationIcon = {
            AppBarIcon(
                painter = rememberVectorPainter("ic_arrow_back"),
                onClick = { navController.popBackStack() },
                tint = CoinTheme.color.primaryVariant
            )
        },
        actions = {
            AppBarIcon(
                modifier = Modifier
                    .clip(RoundedCornerShape(percent = 50))
                    .background(CoinTheme.color.primaryVariant),
                painter = rememberVectorPainter("ic_plus"),
                tint = color,
                height = 34.dp,
                width = 50.dp
            )
        },
        backgroundColor = color
    )
}