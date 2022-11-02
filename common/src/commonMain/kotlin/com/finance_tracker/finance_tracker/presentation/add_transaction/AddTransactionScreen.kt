package com.finance_tracker.finance_tracker.presentation.add_transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.BackHandler
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.toDate
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.finance_tracker.finance_tracker.presentation.add_transaction.views.*
import com.finance_tracker.finance_tracker.presentation.add_transaction.views.enter_transaction_controller.EnterTransactionController
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun AddTransactionScreen() {
    StoredViewModel<AddTransactionViewModel> { viewModel ->
        val navController = LocalRootController.current
        LaunchedEffect(Unit) { viewModel.onScreenComposed() }

        val selectedTransactionType by viewModel.selectedTransactionType.collectAsState()
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val amountText by viewModel.amount.collectAsState()
            val accountData by viewModel.selectedAccount.collectAsState()
            val categoryData by viewModel.selectedCategory.collectAsState()
            val localDate by viewModel.selectedDate.collectAsState()

            val isAddTransactionEnabled = accountData != null && categoryData != null
            val onAddTransaction = {
                val account = accountData
                if (account != null) {
                    viewModel.addTransaction(
                        Transaction(
                            type = selectedTransactionType,
                            amountCurrency = "$",
                            account = account,
                            category = categoryData,
                            amount = amountText.toDouble(),
                            date = localDate.toDate()
                        )
                    )
                    navController.popBackStack()
                }
            }
            CategoriesAppBar(
                doneButtonEnabled = isAddTransactionEnabled,
                selectedTransactionType = selectedTransactionType,
                onTransactionTypeSelect = viewModel::onTransactionTypeSelect,
                onDoneClick = onAddTransaction
            )

            AmountTextField(
                modifier = Modifier
                    .weight(1f),
                currency = "$",
                amount = amountText
            )

            CalendarDayView(
                date = localDate,
                onDateChange = viewModel::onDateSelect
            )

            Surface(
                modifier = Modifier.weight(2f),
                elevation = 8.dp
            ) {

                var currentStep by rememberSaveable { mutableStateOf(viewModel.firstStep) }
                var previousStepIndex by rememberSaveable { mutableStateOf(-1) }
                BackHandler {
                    if (currentStep != viewModel.firstStep) {
                        currentStep = currentStep.previous()
                    } else {
                        navController.popBackStack()
                    }
                }
                LaunchedEffect(currentStep) {
                    previousStepIndex = currentStep.ordinal
                }

                Column {
                    StepsEnterTransactionBar(
                        data = StepsEnterTransactionBarData(
                            currentStep = currentStep,
                            accountData = accountData,
                            categoryData = categoryData
                        ),
                        onStepSelect = { currentStep = it }
                    )

                    val categoriesFlow = when (selectedTransactionType) {
                        TransactionType.Expense -> viewModel.expenseCategories
                        TransactionType.Income -> viewModel.incomeCategories
                    }
                    val accounts by viewModel.accounts.collectAsState()
                    val categories by categoriesFlow.collectAsState()
                    EnterTransactionController(
                        modifier = Modifier.weight(1f),
                        accounts = accounts,
                        categories = categories,
                        currentStep = currentStep,
                        animationDirection = if (currentStep.ordinal >= previousStepIndex) {
                            1
                        } else {
                            -1
                        },
                        onAccountSelect = {
                            viewModel.onAccountSelect(it)
                            currentStep = currentStep.next()
                        },
                        onCategorySelect = {
                            viewModel.onCategorySelect(it)
                            currentStep = currentStep.next()
                        },
                        onKeyboardButtonClick = { command ->
                            viewModel.onKeyboardButtonClick(command)
                        }
                    )

                    AddButtonSection(
                        enabled = isAddTransactionEnabled,
                        onAddClick = onAddTransaction
                    )
                }
            }
        }
    }
}