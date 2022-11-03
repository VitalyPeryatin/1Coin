package com.finance_tracker.finance_tracker.core.ui.transactions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.DateFormatType
import com.finance_tracker.finance_tracker.core.common.DecimalFormatType
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import java.text.SimpleDateFormat
import java.util.*

private val DateFormatter = SimpleDateFormat("dd.MM")

@Composable
fun CommonTransactionsList(
    transactions: List<TransactionListModel>,
    modifier: Modifier = Modifier
) {
    if (transactions.isEmpty()) {
        EmptyTransactionsStub(
            modifier = modifier
        )
    } else {
        TransactionsList(
            modifier = modifier,
            transactions = transactions
        )
    }
}

@Composable
private fun TransactionsList(
    transactions: List<TransactionListModel>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        items(transactions) { transactionModel ->
            when (transactionModel) {
                is TransactionListModel.Data -> {
                    TransactionItem(transaction = transactionModel.transaction)
                }
                is TransactionListModel.DateAndDayTotal -> {
                    DayTotalHeader(dayTotalModel = transactionModel)
                }
            }
        }
    }
}

@Composable
private fun DayTotalHeader(
    modifier: Modifier = Modifier,
    dayTotalModel: TransactionListModel.DateAndDayTotal
) {
    Row(
        modifier = modifier
            .padding(top = 24.dp, bottom = 8.dp)
    ) {
        val formattedDate = when {
            dayTotalModel.date.isToday() -> {
                stringResource("transactions_today")
            }
            dayTotalModel.date.isYesterday() -> {
                stringResource("transactions_yesterday")
            }
            else -> {
                DateFormatter.format(dayTotalModel.date)
            }
        }
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = formattedDate,
            style = CoinTheme.typography.subtitle2,
            color = CoinTheme.color.secondary
        )
        Spacer(modifier = Modifier
            .weight(1f)
            .defaultMinSize(minWidth = 16.dp))
        Text(
            modifier = Modifier.padding(end = 16.dp),
            text = "+${DecimalFormatType.Amount.format(dayTotalModel.income)}",
            style = CoinTheme.typography.subtitle2,
            color = CoinTheme.color.accentGreen
        )

        Text(
            modifier = Modifier.padding(end = 16.dp),
            text = "-${DecimalFormatType.Amount.format(dayTotalModel.expense)}",
            style = CoinTheme.typography.subtitle2,
            color = CoinTheme.color.content
        )
    }
}

@Composable
private fun EmptyTransactionsStub(
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = "", // TODO: Empty text
        textAlign = TextAlign.Center
    )
}

private fun Date.isToday(): Boolean {
    val nowDateString = DateFormatType.CommonDateFormat.format(Date())
    val currentDateString = DateFormatType.CommonDateFormat.format(this)
    return nowDateString == currentDateString
}

private fun Date.isYesterday(): Boolean {
    val previousDate = Calendar.getInstance().apply {
        time = Date()
        add(Calendar.DAY_OF_YEAR, -1)
    }.time
    val currentDateString = DateFormatType.CommonDateFormat.format(this)
    val previousDateString = DateFormatType.CommonDateFormat.format(previousDate)
    return previousDateString == currentDateString
}