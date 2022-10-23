package com.finance_tracker.finance_tracker.presentation.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.DateFormatType
import com.finance_tracker.finance_tracker.core.common.DecimalFormatType
import com.finance_tracker.finance_tracker.core.common.LocalContext
import com.finance_tracker.finance_tracker.core.common.getLocalizedString
import com.finance_tracker.finance_tracker.core.common.getViewModel
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.loadXmlPicture
import com.finance_tracker.finance_tracker.domain.models.Category
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.finance_tracker.finance_tracker.presentation.transactions.views.TransactionsAppBar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

private val DateFormatter = SimpleDateFormat("dd.mm")

@Composable
fun TransactionsScreen(
    viewModel: TransactionsViewModel = getViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TransactionsAppBar()

        val transactions by viewModel.transactions.collectAsState()
        if (transactions.isEmpty()) {
            EmptyTransactionsStub()
        } else {
            TransactionsList(transactions = transactions)
        }
    }
}

@Composable
fun TransactionsList(
    modifier: Modifier = Modifier,
    transactions: List<TransactionUiModel>
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        items(transactions) { transactionModel ->
            when (transactionModel) {
                is TransactionUiModel.Data -> {
                    TransactionItem(transaction = transactionModel.transaction)
                }
                is TransactionUiModel.DateAndDayTotal -> {
                    DayTotalHeader(dayTotalModel = transactionModel)
                }
            }
        }
    }
}

@Composable
fun EmptyTransactionsStub(
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = "", // TODO: Empty text
        textAlign = TextAlign.Center
    )
}

@Composable
private fun TransactionItem(
    transaction: Transaction,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val category = transaction.category ?: Category.EMPTY
    Row(
        modifier = modifier
            .clickable { onClick.invoke() }
            .padding(vertical = 6.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(44.dp)
                .background(color = CoinTheme.color.secondaryBackground, shape = CircleShape)
                .padding(12.dp),
            imageVector = loadXmlPicture(category.iconId),
            contentDescription = null
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .weight(1f)
        ) {
            Text(
                text = category.name,
                style = CoinTheme.typography.body2
            )
            Text(
                text = transaction.account.name,
                style = CoinTheme.typography.subtitle2,
                color = LocalContentColor.current.copy(alpha = 0.5f)
            )
        }

        val sign = if (transaction.type == TransactionType.Income) "+" else "-"
        Text(
            text = sign + transaction.amountCurrency + DecimalFormatType.Amount.format(transaction.amount),
            style = CoinTheme.typography.body2
        )
    }
}

@Composable
private fun DayTotalHeader(
    modifier: Modifier = Modifier,
    dayTotalModel: TransactionUiModel.DateAndDayTotal
) {
    val context = LocalContext.current
    Row(
        modifier = modifier
            .padding(top = 24.dp, bottom = 8.dp)
    ) {
        val formattedDate = when {
            dayTotalModel.date.isToday() -> {
                getLocalizedString("transactions_today", context)
            }
            dayTotalModel.date.isYesterday() -> {
                getLocalizedString("transactions_yesterday", context)
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