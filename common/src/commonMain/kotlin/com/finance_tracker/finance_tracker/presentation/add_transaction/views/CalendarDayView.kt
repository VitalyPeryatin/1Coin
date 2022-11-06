package com.finance_tracker.finance_tracker.presentation.add_transaction.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.asCalendar
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.CalendarDialog
import com.finance_tracker.finance_tracker.core.ui.CalendarDialogController
import com.finance_tracker.finance_tracker.core.ui.StubCalendarDialogController
import com.finance_tracker.finance_tracker.core.ui.rememberVectorPainter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun CalendarDayView(
    date: LocalDate,
    modifier: Modifier = Modifier,
    onDateChange: (LocalDate) -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        var calendarDialogController: CalendarDialogController by remember {
            mutableStateOf(StubCalendarDialogController)
        }
        val minDate = Calendar.getInstance().apply { add(Calendar.YEAR, -1) }.time
        val maxDate = Date()
        CalendarDialog(
            minDate = minDate.time,
            maxDate = maxDate.time,
            onControllerCreate = {
                calendarDialogController = it
            },
            onDateChangeListener = onDateChange
        )

        Row(
            modifier = Modifier
                .background(CoinTheme.color.background)
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable { calendarDialogController.show() }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CompositionLocalProvider(
                LocalContentColor provides LocalContentColor.current.copy(alpha = 0.8f)
            ) {
                Icon(
                    painter = rememberVectorPainter("ic_calendar"),
                    contentDescription = null,
                    tint = LocalContentColor.current
                )

                val formattedDate = date.format(DateTimeFormatter.ofPattern("dd.MM"))
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = when {
                        date.isToday() -> "${stringResource("add_transaction_today")}, $formattedDate"
                        date.isYesterday() -> "${stringResource("add_transaction_yesterday")}, $formattedDate"
                        else -> formattedDate
                    },
                    style = CoinTheme.typography.subtitle2,
                    color = LocalContentColor.current
                )
            }
        }
    }
}

private fun LocalDate.isToday(): Boolean {
    val todayDate = LocalDate.now()
    return todayDate.dayOfYear == dayOfYear && todayDate.year == year
}

private fun LocalDate.isYesterday(): Boolean {
    val currentCalendar = asCalendar()
    val yesterdayCalendar = Calendar.getInstance().apply {
        add(Calendar.DATE, -1)
    }
    return yesterdayCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR) &&
            yesterdayCalendar.get(Calendar.DAY_OF_YEAR) == currentCalendar.get(Calendar.DAY_OF_YEAR)
}