package com.finance_tracker.finance_tracker.presentation.add_account

import com.finance_tracker.finance_tracker.core.common.Context
import com.finance_tracker.finance_tracker.core.common.getLocalizedString
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
    rootController: RootController
) {
    when (event) {
        AddAccountEvent.Close -> {
            rootController.popBackStack()
        }
        is AddAccountEvent.ShowToast -> {
            // TODO: Вернуть код
            val string = getLocalizedString(event.textId, context)
            println(string)
            //Toast.makeText(context, event.textId, Toast.LENGTH_SHORT).show()
        }
    }
}