package com.finance_tracker.finance_tracker.screens.add_trnsaction

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.R
import com.finance_tracker.finance_tracker.theme.CoinTheme

object StagesNavigationBar {
    enum class Stage(@StringRes val textRes: Int) {
        Account(R.string.add_transaction_stage_account),
        Category(R.string.add_transaction_stage_category)
    }
}

@Composable
fun StagesNavigationBar(
    modifier: Modifier = Modifier,
    selectedStage: StagesNavigationBar.Stage = StagesNavigationBar.Stage.Account,
    onStageSelect: (StagesNavigationBar.Stage) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(CoinTheme.color.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        StageText(
            stage = StagesNavigationBar.Stage.Account,
            selectedStage = selectedStage,
            onStageSelect = onStageSelect
        )

        NextIcon()

        StageText(
            stage = StagesNavigationBar.Stage.Category,
            selectedStage = selectedStage,
            onStageSelect = onStageSelect
        )
    }
}

@Composable
fun NextIcon(
    modifier: Modifier = Modifier
) {
    Icon(
        modifier = modifier
            .padding(horizontal = 8.dp),
        painter = painterResource(R.drawable.ic_arrow_next_small),
        contentDescription = null
    )
}

@Composable
private fun RowScope.StageText(
    stage: StagesNavigationBar.Stage,
    selectedStage: StagesNavigationBar.Stage,
    modifier: Modifier = Modifier,
    onStageSelect: (StagesNavigationBar.Stage) -> Unit
) {
    val isActiveStage = stage == selectedStage
    Text(
        modifier = modifier
            .weight(1f)
            .clickable(enabled = !isActiveStage) { onStageSelect.invoke(stage) }
            .padding(vertical = 16.dp, horizontal = 8.dp),
        text = stringResource(stage.textRes),
        style = CoinTheme.typography.subtitle1,
        textAlign = TextAlign.Center,
        color = if (isActiveStage) {
            LocalContentColor.current
        } else {
            LocalContentColor.current.copy(alpha = 0.3f)
        }
    )
}

@Preview
@Composable
fun StagesNavigationBarPreview() {
    StagesNavigationBar()
}