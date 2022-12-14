package com.finance_tracker.finance_tracker.presentation.analytics.delegates

import androidx.compose.runtime.Composable
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.data.repositories.CurrenciesRepository
import com.finance_tracker.finance_tracker.domain.models.Amount
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.CurrencyRates
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.finance_tracker.finance_tracker.presentation.analytics.analytics.AnalyticsScreenAnalytics
import com.finance_tracker.finance_tracker.presentation.analytics.models.TrendBarDetails
import com.finance_tracker.finance_tracker.presentation.analytics.peroid_bar_chart.PeriodChip
import com.finance_tracker.finance_tracker.presentation.common.formatters.AmountFormatMode
import com.finance_tracker.finance_tracker.presentation.common.formatters.format
import com.financetracker.financetracker.data.GetTransactionsForPeriod
import com.financetracker.financetracker.data.TransactionsEntityQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar
import java.util.Date
import java.util.LinkedList
import java.util.SortedMap
import kotlin.coroutines.CoroutineContext

class TrendsAnalyticsDelegate(
    private val transactionsEntityQueries: TransactionsEntityQueries,
    currenciesRepository: CurrenciesRepository,
    private val analyticsScreenAnalytics: AnalyticsScreenAnalytics
): CoroutineScope {

    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main.immediate

    private val databaseCurrencyRatesFlow = currenciesRepository.getCurrencyRatesFlow()
    private val primaryCurrencyFlow = currenciesRepository.getPrimaryCurrencyFlow()

    private val _selectedPeriodChipFlow = MutableStateFlow(PeriodChip.Week)
    val selectedPeriodChipFlow = _selectedPeriodChipFlow.asStateFlow()

    private val selectedTransactionTypeFlow = MutableStateFlow(TransactionType.Expense)

    private val weekTrendTransform: (
        List<GetTransactionsForPeriod>, CurrencyRates, Currency
    ) -> List<TrendBarDetails> = { transactions, currencyRates, primaryCurrency ->
        associateTransactionsByBars(
            transactions = transactions,
            barsRange = 1..DaysInWeek,
            transformDateToBarOrder = { calendar ->
                var dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
                if (dayOfWeek == Calendar.SUNDAY) {
                    dayOfWeek = 8
                }
                dayOfWeek -= 1
                return@associateTransactionsByBars dayOfWeek
            },
            currencyRates = currencyRates,
            primaryCurrency = primaryCurrency
        )
            .map { (dayOfWeek, txsAmounts) ->
                mapTxsAmountsToTrendBarDetails(
                    txsAmounts = txsAmounts,
                    primaryCurrency = primaryCurrency,
                    title = { stringResource("week_day_$dayOfWeek") }
                )
            }
    }

    private val monthTrendTransform: (
        List<GetTransactionsForPeriod>, CurrencyRates, Currency
    ) -> List<TrendBarDetails> = { transactions, currencyRates, primaryCurrency ->
        val startDayOfMonthEpochMillis = getStartOfMonthDate().time
        val maxDayOfMonth = Calendar.getInstance().apply {
            timeInMillis = startDayOfMonthEpochMillis
            add(Calendar.DAY_OF_YEAR, -1)
        }.get(Calendar.DAY_OF_MONTH)

        associateTransactionsByBars(
            transactions = transactions,
            barsRange = 1..maxDayOfMonth,
            transformDateToBarOrder = { calendar -> calendar.get(Calendar.DAY_OF_MONTH) },
            currencyRates = currencyRates,
            primaryCurrency = primaryCurrency
        )
            .map { (dayOfMonth, txsAmounts) ->
                mapTxsAmountsToTrendBarDetails(
                    txsAmounts = txsAmounts,
                    primaryCurrency = primaryCurrency,
                    title = {
                        "$dayOfMonth ${stringResource("month_${Calendar.getInstance().get(Calendar.MONTH) + 1}")}"
                    }
                )
            }
    }

    private val yearTrendTransform: (
        List<GetTransactionsForPeriod>, CurrencyRates, Currency
    ) -> List<TrendBarDetails> = { transactions, currencyRates, primaryCurrency ->
        associateTransactionsByBars(
            transactions = transactions,
            barsRange = 1..MonthsInYear,
            transformDateToBarOrder = { calendar -> calendar.get(Calendar.MONTH) + 1 },
            currencyRates = currencyRates,
            primaryCurrency = primaryCurrency
        )
            .map { (monthNumberOfYear, txsAmounts) ->
                mapTxsAmountsToTrendBarDetails(
                    txsAmounts = txsAmounts,
                    primaryCurrency = primaryCurrency,
                    title = { stringResource("month_$monthNumberOfYear") }
                )
            }
    }

    private val expenseWeekTrendFlow = subscribeTransactionsData(
        transactionType = TransactionType.Expense,
        fromDate = getStartOfWeekDate(),
        toDate = getStartOfNextWeekDate(),
        transform = weekTrendTransform
    )

    private val incomeWeekTrendFlow = subscribeTransactionsData(
        transactionType = TransactionType.Income,
        fromDate = getStartOfWeekDate(),
        toDate = getStartOfNextWeekDate(),
        transform = weekTrendTransform
    )

    private val expenseMonthTrendFlow = subscribeTransactionsData(
        transactionType = TransactionType.Expense,
        fromDate = getStartOfMonthDate(),
        toDate = getStartOfNextMonthDate(),
        transform = monthTrendTransform
    )

    private val incomeMonthTrendFlow = subscribeTransactionsData(
        transactionType = TransactionType.Income,
        fromDate = getStartOfMonthDate(),
        toDate = getStartOfNextMonthDate(),
        transform = monthTrendTransform
    )

    private val expenseYearTrendFlow = subscribeTransactionsData(
        transactionType = TransactionType.Expense,
        fromDate = getStartOfYearDate(),
        toDate = getStartOfNextYearDate(),
        transform = yearTrendTransform
    )

    private val incomeYearTrendFlow = subscribeTransactionsData(
        transactionType = TransactionType.Income,
        fromDate = getStartOfYearDate(),
        toDate = getStartOfNextYearDate(),
        transform = yearTrendTransform
    )

    private val expenseFlow = combine(
        selectedPeriodChipFlow, expenseWeekTrendFlow, expenseMonthTrendFlow, expenseYearTrendFlow
    ) { selectedPeriodChip, expenseWeekTrend, expenseMonthTrend, expenseYearTrend ->
        when (selectedPeriodChip) {
            PeriodChip.Week -> expenseWeekTrend
            PeriodChip.Month -> expenseMonthTrend
            PeriodChip.Year -> expenseYearTrend
        }
    }
        .flowOn(Dispatchers.IO)
        .stateIn(this@TrendsAnalyticsDelegate, started = SharingStarted.Lazily, initialValue = listOf())

    private val incomeFlow = combine(
        selectedPeriodChipFlow, incomeWeekTrendFlow, incomeMonthTrendFlow, incomeYearTrendFlow
    ) { selectedPeriodChip, incomeWeekTrend, incomeMonthTrend, incomeYearTrend ->
        when (selectedPeriodChip) {
            PeriodChip.Week -> incomeWeekTrend
            PeriodChip.Month -> incomeMonthTrend
            PeriodChip.Year -> incomeYearTrend
        }
    }
        .flowOn(Dispatchers.IO)
        .stateIn(this@TrendsAnalyticsDelegate, started = SharingStarted.Lazily, initialValue = listOf())

    val trendFlow = combine(selectedTransactionTypeFlow, expenseFlow, incomeFlow) {
            selectedTransactionType, expense, income ->

        when (selectedTransactionType) {
            TransactionType.Expense -> expense
            TransactionType.Income -> income
        }
    }
        .flowOn(Dispatchers.IO)
        .stateIn(this@TrendsAnalyticsDelegate, started = SharingStarted.Lazily, initialValue = listOf())

    val totalFlow = combine(trendFlow, primaryCurrencyFlow) {
        trend, primaryCurrency ->
        Amount(
            amountValue = trend.sumOf { it.value },
            currency = primaryCurrency
        )
    }
        .flowOn(Dispatchers.IO)
        .stateIn(this@TrendsAnalyticsDelegate, started = SharingStarted.Lazily, initialValue = Amount.default)

    init {
        subscribeAnalyticsEvents()
    }

    private fun subscribeAnalyticsEvents() {
        combine(selectedTransactionTypeFlow, selectedPeriodChipFlow) { selectedTransactionType, selectedPeriodChip ->
            analyticsScreenAnalytics.trackTrendEvent(selectedTransactionType, selectedPeriodChip)
        }.launchIn(this)
    }

    private fun associateTransactionsByBars(
        transactions: List<GetTransactionsForPeriod>,
        barsRange: IntRange,
        transformDateToBarOrder: (Calendar) -> Int,
        currencyRates: CurrencyRates,
        primaryCurrency: Currency
    ): SortedMap<Int, LinkedList<Double>> {
        val map = mutableMapOf<Int, LinkedList<Double>>()
        barsRange.onEach { map[it] = LinkedList() }

        transactions.forEach { transaction ->
            val calendar = Calendar.getInstance().apply {
                timeInMillis = transaction.date.time
            }

            val amount = Amount(
                currency = Currency.getByCode(transaction.amountCurrency),
                amountValue = transaction.amount
            ).convertToCurrency(
                currencyRates = currencyRates,
                toCurrency = primaryCurrency
            )

            val dayOfWeek = transformDateToBarOrder.invoke(calendar)
            map[dayOfWeek]?.add(amount)
        }

        return map.toSortedMap()
    }

    private fun mapTxsAmountsToTrendBarDetails(
        txsAmounts: List<Double>,
        primaryCurrency: Currency,
        title: @Composable () -> String
    ): TrendBarDetails {
        val value = txsAmounts.sum()
        return TrendBarDetails(
            title = title,
            formattedValue = {
                Amount(
                    amountValue = value,
                    currency = primaryCurrency
                ).format(mode = AmountFormatMode.NoSigns)
            },
            value = value
        )
    }

    private fun subscribeTransactionsData(
        transactionType: TransactionType,
        fromDate: Date,
        toDate: Date,
        transform: (List<GetTransactionsForPeriod>, CurrencyRates, Currency) -> List<TrendBarDetails>
    ): Flow<List<TrendBarDetails>> {
        return combine(transactionsEntityQueries.getTransactionsForPeriod(
            transactionType = transactionType,
            fromDate = fromDate,
            toDate = toDate
        ).asFlow().mapToList(), databaseCurrencyRatesFlow, primaryCurrencyFlow, transform = transform)
            .flowOn(Dispatchers.IO)
            .stateIn(this@TrendsAnalyticsDelegate, started = SharingStarted.Lazily, initialValue = listOf())
    }

    private fun getStartOfWeekDate(): Date {
        return Calendar.getInstance().apply {
            var dayOfWeek = get(Calendar.DAY_OF_WEEK)
            if (dayOfWeek == Calendar.SUNDAY) {
                dayOfWeek = Calendar.SATURDAY + 1
            }
            add(Calendar.DAY_OF_WEEK, -(dayOfWeek - 2))
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
    }

    private fun getStartOfNextWeekDate(): Date {
        return Calendar.getInstance().apply {
            time = getStartOfWeekDate()
            add(Calendar.DAY_OF_YEAR, DaysInWeek)
        }.time
    }

    private fun getStartOfMonthDate(): Date {
        return Calendar.getInstance().apply {
            val year = get(Calendar.YEAR)
            val month = get(Calendar.MONTH)
            timeInMillis = 0L
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
        }.time
    }

    private fun getStartOfNextMonthDate(): Date {
        return Calendar.getInstance().apply {
            time = getStartOfMonthDate()
            add(Calendar.MONTH, 1)
        }.time
    }

    private fun getStartOfYearDate(): Date {
        return Calendar.getInstance().apply {
            val year = get(Calendar.YEAR)
            timeInMillis = 0L
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, Calendar.JANUARY)
        }.time
    }

    private fun getStartOfNextYearDate(): Date {
        return Calendar.getInstance().apply {
            time = getStartOfYearDate()
            add(Calendar.YEAR, 1)
        }.time
    }

    fun setPeriodChip(periodChip: PeriodChip) {
        _selectedPeriodChipFlow.value = periodChip
    }

    fun setSelectedTransactionType(transactionType: TransactionType) {
        selectedTransactionTypeFlow.value = transactionType
    }

    companion object {
        private const val DaysInWeek = 7
        private const val MonthsInYear = 12
    }
}