package com.finance_tracker.finance_tracker.presentation.analytics.peroid_bar_chart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

private val ChartHeight = 160.dp

@Composable
fun PeriodBarChart(
    defaultTitle: String,
    defaultValue: String,
    barChartEntries: List<CoinBarChartEntry<Float, Float>>,
    modifier: Modifier = Modifier,
    selectedPeriodChip: PeriodChip = PeriodChip.Week,
    onChipSelect: (PeriodChip) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var selectedBarChatEntity by remember { mutableStateOf<CoinBarChartEntry<*, *>?>(null) }

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = selectedBarChatEntity?.overviewTitle ?: defaultTitle,
            style = CoinTheme.typography.subtitle2_medium,
            color = CoinTheme.color.content.copy(alpha = 0.5f)
        )

        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = selectedBarChatEntity?.overviewValue ?: defaultValue,
            style = CoinTheme.typography.h2,
            color = if (selectedBarChatEntity == null) {
                CoinTheme.color.content
            } else {
                CoinTheme.color.primary
            }
        )

        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            val chips = remember { PeriodChip.values() }
            chips.forEach { chip ->
                CoinChip(
                    text = stringResource(chip.textId),
                    selected = chip == selectedPeriodChip,
                    onClick = { onChipSelect.invoke(chip) }
                )
            }
        }

        CoinBarChart(
            modifier = Modifier.padding(horizontal = 16.dp),
            barChartEntries = barChartEntries,
            labelsArrangement = getLabelsArrangementFor(selectedPeriodChip),
            labels = getLabelsFor(selectedPeriodChip),
            chartHeight = ChartHeight,
            onBarChartSelect = { selectedBarChatEntity = it }
        )
    }
}

@Composable
private fun getLabelsArrangementFor(periodChip: PeriodChip): LabelsArrangement {
    return if (periodChip == PeriodChip.Month) {
        LabelsArrangement.SpaceBetween
    } else {
        LabelsArrangement.SpaceAround
    }
}

@Composable
private fun getLabelsFor(periodChip: PeriodChip): List<String> {
    val now = remember {
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    }
    val monthValueString by remember(now) {
        derivedStateOf {
            String.format(format = "%02d", now.monthNumber)
        }
    }
    val lastDayOfMonth by remember(now) {
        derivedStateOf {
            val currentMonth = LocalDate(year = now.year, month = now.month, dayOfMonth = 1)
            val nextMonth = LocalDate(year = now.year, month = now.month + 1, dayOfMonth = 1)
            val days = nextMonth.toEpochDays() - currentMonth.toEpochDays()
            String.format(format = "%02d", days)
        }
    }

    return when (periodChip) {
        PeriodChip.Week -> {
            listOf(
                stringResource("day_of_week_mon"),
                stringResource("day_of_week_tue"),
                stringResource("day_of_week_wed"),
                stringResource("day_of_week_thu"),
                stringResource("day_of_week_fri"),
                stringResource("day_of_week_sat"),
                stringResource("day_of_week_sun"),
            )
        }
        PeriodChip.Month -> {
            listOf(
                "01.$monthValueString",
                "10.$monthValueString",
                "20.$monthValueString",
                "$lastDayOfMonth.$monthValueString"
            )
        }
        PeriodChip.Year -> {
            listOf(
                stringResource("month_jan"),
                "",
                stringResource("month_mar"),
                "",
                stringResource("month_may"),
                "",
                stringResource("month_jul"),
                "",
                stringResource("month_sep"),
                "",
                stringResource("month_nov"),
                ""
            )
        }
    }
}

enum class PeriodChip(
    val textId: String,
    val analyticsName: String
) {
    Week(
        textId = "week",
        analyticsName = "Week"
    ),
    Month(
        textId = "month",
        analyticsName = "Month"
    ),
    Year(
        textId = "year",
        analyticsName = "Year"
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CoinChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Chip(
        modifier = modifier,
        colors = ChipDefaults.chipColors(
            backgroundColor = if (selected) {
                CoinTheme.color.secondaryBackground
            } else {
                Color.Transparent
            },
            contentColor = if (selected) {
                CoinTheme.color.content
            } else {
                CoinTheme.color.secondary
            },
            disabledBackgroundColor = Color.Transparent,
            disabledContentColor = CoinTheme.color.secondary
        ),
        shape = RoundedCornerShape(percent = 50),
        onClick = onClick
    ) {
        Text(
            text = text,
            style = CoinTheme.typography.subtitle2_medium
        )
    }
}