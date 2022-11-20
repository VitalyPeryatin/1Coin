package com.finance_tracker.finance_tracker.presentation.add_transaction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.BackHandler
import com.finance_tracker.finance_tracker.core.common.DialogConfigurations
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.`if`
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.common.toDate
import com.finance_tracker.finance_tracker.core.ui.DeleteDialog
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.finance_tracker.finance_tracker.domain.models.TransactionType
import com.finance_tracker.finance_tracker.presentation.add_transaction.views.ActionButtonsSection
import com.finance_tracker.finance_tracker.presentation.add_transaction.views.AmountTextField
import com.finance_tracker.finance_tracker.presentation.add_transaction.views.CalendarDayView
import com.finance_tracker.finance_tracker.presentation.add_transaction.views.CategoriesAppBar
import com.finance_tracker.finance_tracker.presentation.add_transaction.views.EnterTransactionStep
import com.finance_tracker.finance_tracker.presentation.add_transaction.views.StepsEnterTransactionBar
import com.finance_tracker.finance_tracker.presentation.add_transaction.views.StepsEnterTransactionBarData
import com.finance_tracker.finance_tracker.presentation.add_transaction.views.enter_transaction_controller.EnterTransactionController
import org.koin.core.parameter.parametersOf
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun AddTransactionScreen(
    transaction: Transaction
) {
    StoredViewModel<AddTransactionViewModel>(
        parameters = { parametersOf(transaction) }
    ) { viewModel ->
        val navController = LocalRootController.current
        val modalNavController = navController.findModalController()
        LaunchedEffect(Unit) { viewModel.onScreenComposed() }

        val selectedTransactionType by viewModel.selectedTransactionType.collectAsState()
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val amountText by viewModel.amount.collectAsState()
            val accountData by viewModel.selectedAccount.collectAsState()
            val categoryData by viewModel.selectedCategory.collectAsState()
            val localDate by viewModel.selectedDate.collectAsState()
            val currency by viewModel.currency.collectAsState()

            val isAddTransactionEnabled = accountData != null && categoryData != null
            val onAddTransaction = {
                val account = accountData
                if (account != null) {
                    viewModel.onAddTransactionClick(
                        Transaction(
                            type = selectedTransactionType,
                            amountCurrency = currency,
                            account = account,
                            category = categoryData,
                            amount = amountText.toDouble(),
                            date = localDate.toDate()
                        )
                    )
                    navController.popBackStack()
                }
            }
            val onEditTransaction = {
                val account = accountData
                if (account != null) {
                    viewModel.onEditTransactionClick(
                        Transaction(
                            type = selectedTransactionType,
                            amountCurrency = currency,
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
                onDoneClick = {
                    if (viewModel.isEditMode) {
                        onEditTransaction.invoke()
                    } else {
                        onAddTransaction.invoke()
                    }
                }
            )

            var currentStep by rememberSaveable { mutableStateOf(viewModel.firstStep) }

            AmountTextField(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        currentStep = EnterTransactionStep.Amount
                    },
                currency = currency.sign,
                amount = amountText
            )

            CalendarDayView(
                date = localDate,
                onDateChange = viewModel::onDateSelect
            )

            Surface(
                modifier = Modifier
                    .`if`(currentStep != null) {
                        weight(2f)
                    },
                elevation = 8.dp
            ) {
                var previousStepIndex by rememberSaveable {
                    mutableStateOf(currentStep?.let { it.ordinal - 1 })
                }
                BackHandler {
                    if (currentStep != viewModel.firstStep) {
                        currentStep = currentStep?.previous()
                    } else {
                        navController.popBackStack()
                    }
                }
                LaunchedEffect(currentStep) {
                    previousStepIndex = currentStep?.ordinal
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
                        modifier = Modifier
                            .`if`(currentStep == null) {
                                height(0.dp)
                            }
                            .`if`(currentStep != null) {
                                weight(1f)
                            },
                        accounts = accounts,
                        categories = categories,
                        currentStep = currentStep,
                        animationDirection = when {
                            currentStep == null || previousStepIndex == null -> 0
                            currentStep!!.ordinal >= previousStepIndex!! -> 1
                            else -> -1
                        },
                        onAccountSelect = {
                            viewModel.onAccountSelect(it)
                            currentStep = currentStep?.next()
                        },
                        onCategorySelect = {
                            viewModel.onCategorySelect(it)
                            currentStep = currentStep?.next()
                        },
                        onKeyboardButtonClick = { command ->
                            viewModel.onKeyboardButtonClick(command)
                        }
                    )

                    ActionButtonsSection(
                        enabled = isAddTransactionEnabled,
                        onAddClick = onAddTransaction,
                        onEditClick = onEditTransaction,
                        isEditMode = viewModel.isEditMode,
                        onDeleteClick = {
                            modalNavController.present(DialogConfigurations.alert) { key ->
                                DeleteDialog(
                                    titleEntity = stringResource("transaction"),
                                    onCancelClick = {
                                        modalNavController.popBackStack(key, animate = false)
                                    },
                                    onDeleteClick = {
                                        viewModel.onDeleteTransactionClick(transaction)
                                        modalNavController.popBackStack(key, animate = false)
                                        navController.popBackStack()
                                    }
                                )
                            }
                        },
                        onDuplicateClick = {
                            viewModel.onDuplicateTransactionClick(transaction)
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}