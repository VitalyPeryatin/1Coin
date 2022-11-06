package com.finance_tracker.finance_tracker.data.broadcast_receivers.parsers

import android.telephony.SmsMessage
import androidx.compose.ui.graphics.Color
import com.finance_tracker.finance_tracker.data.broadcast_receivers.parsers.models.SmsTemplate
import com.finance_tracker.finance_tracker.data.broadcast_receivers.parsers.models.TransactionFields
import com.finance_tracker.finance_tracker.data.broadcast_receivers.parsers.models.getAmount
import com.finance_tracker.finance_tracker.data.broadcast_receivers.parsers.models.getAmountCurrency
import com.finance_tracker.finance_tracker.data.broadcast_receivers.parsers.models.getTransactionType
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Currency
import com.finance_tracker.finance_tracker.domain.models.Transaction
import java.util.*

class SmsMessageParser(
    private val templates: List<SmsTemplate>
) {

    fun parseMessage(smsMessage: SmsMessage): Transaction? {
        val date = Date(smsMessage.timestampMillis)
        val message = smsMessage.displayMessageBody
        val supportedTemplate = getSupportedTemplate(smsMessage) ?: return null

        val fields = groupByTransactionFields(message, supportedTemplate)
        return Transaction(
            id = -1,
            type = fields.getTransactionType() ?: return null,
            amount = fields.getAmount(),
            account = Account(
                id = 1,
                type = Account.Type.DebitCard,
                name = "Fake card",
                balance = 0.0,
                color = Color.Red,
                currency = Currency.list.first()
            ),
            amountCurrency = fields.getAmountCurrency(),
            category = null,
            date = date
        )
    }

    private fun getSupportedTemplate(smsMessage: SmsMessage): SmsTemplate? {
        val message = smsMessage.displayMessageBody
        val fromAddress = smsMessage.originatingAddress
        return templates
            .firstOrNull { fromAddress in it.fromAddresses && it.regex.matches(message) }
    }

    private fun groupByTransactionFields(
        message: String,
        smsTemplate: SmsTemplate
    ): TransactionFields {
        val regex = smsTemplate.regex
        val groupNames = smsTemplate.transactionFields

        val groupValues = regex.findAll(message).first()
            .groups.toMutableList()
            .apply { removeAt(0) }
            .mapNotNull { it?.value }
        return groupNames.zip(groupValues).toMap()
    }
}