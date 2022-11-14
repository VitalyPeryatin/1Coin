package com.finance_tracker.finance_tracker.presentation.add_account

import androidx.compose.material.ScaffoldState
import com.finance_tracker.finance_tracker.core.common.Context
import com.finance_tracker.finance_tracker.core.common.getLocalizedString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.alexgladkov.odyssey.compose.RootController

sealed class AddAccountEvent {
    object Close: AddAccountEvent()
    data class ShowToast(
        val textId: String
    ): AddAccountEvent()
}

fun handleEvent(
    event: AddAccountEvent,
    context: Context,
    rootController: RootController,
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState
) {
    when (event) {
        AddAccountEvent.Close -> {
            rootController.popBackStack()
        }
        is AddAccountEvent.ShowToast -> {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = getLocalizedString(event.textId, context),
                    actionLabel = getLocalizedString("got_it", context)
                )
            }
        }
    }
}