package com.finance_tracker.finance_tracker.presentation.detail_account.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.CollapsingToolbarScaffoldState
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.CollapsingToolbarScopeInstance.road
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.animate
import com.finance_tracker.finance_tracker.core.ui.collapsing_toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun AccountNameText(
    name: String,
    modifier: Modifier = Modifier,
    state: CollapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
) {
    Text(
        modifier = modifier
            .statusBarsPadding()
            .padding(start = state.animate(16.dp, 64.dp))
            .padding(top = state.animate(0.dp, 16.dp))
            .padding(bottom = 16.dp)
            .road(Alignment.BottomStart, Alignment.BottomStart),
        text = name,
        style = CoinTheme.typography.h5.copy(
            fontWeight = FontWeight(state.animate(400, 500)),
            fontSize = state.animate(12.sp, 18.sp),
            color = CoinTheme.color.primaryVariant.copy(
                alpha = state.animate(0.5f, 1f)
            )
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}