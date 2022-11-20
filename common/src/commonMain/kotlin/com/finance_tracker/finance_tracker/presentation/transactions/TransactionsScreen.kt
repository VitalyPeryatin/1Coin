package com.finance_tracker.finance_tracker.presentation.transactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.finance_tracker.finance_tracker.core.common.DialogConfigurations
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.navigation.main.MainNavigationTree
import com.finance_tracker.finance_tracker.core.ui.DeleteDialog
import com.finance_tracker.finance_tracker.core.ui.transactions.CommonTransactionsList
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import com.finance_tracker.finance_tracker.presentation.transactions.views.TransactionsAppBar
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun TransactionsScreen() {
    StoredViewModel<TransactionsViewModel> { viewModel ->
        LaunchedEffect(Unit) {
            viewModel.loadTransactions()
        }
        val navController = LocalRootController.current.findRootController()
        val modalController = navController.findModalController()

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            val transactions by viewModel.transactions.collectAsState()
            val selectedItems by remember(transactions) {
                derivedStateOf {
                    transactions
                        .filterIsInstance<TransactionListModel.Data>()
                        .filter { it.isSelected.value }
                }
            }

            fun unselectAllItems() {
                selectedItems.forEach {
                    it.isSelected.value = false
                }
            }

            val selectedItemsCount by remember {
                derivedStateOf { selectedItems.count() }
            }

            TransactionsAppBar(
                selectedItemsCount = selectedItemsCount,
                onCloseClick = { unselectAllItems() },
                onDeleteClick = {
                    modalController.present(DialogConfigurations.alert) { key ->
                        DeleteDialog(
                            titleEntity = if (selectedItemsCount > 1) {
                                stringResource("transactions")
                            } else {
                                stringResource("transaction")
                            },
                            onCancelClick = {
                                modalController.popBackStack(key, animate = false)
                                unselectAllItems()
                            },
                            onDeleteClick = {
                                viewModel.onDeleteTransactions(
                                    transactions = selectedItems
                                )
                                modalController.popBackStack(key, animate = false)
                                unselectAllItems()
                            }
                        )
                    }
                }
            )

            CommonTransactionsList(
                transactions = transactions,
                onClick = { transactionData ->
                    if (selectedItemsCount > 0) {
                        transactionData.isSelected.value = !transactionData.isSelected.value
                    } else {
                        navController.push(
                            screen = MainNavigationTree.AddTransaction.name,
                            params = transactionData.transaction
                        )
                    }
                },
                onLongClick = {
                    it.isSelected.value = !it.isSelected.value
                }
            )
        }
    }
}