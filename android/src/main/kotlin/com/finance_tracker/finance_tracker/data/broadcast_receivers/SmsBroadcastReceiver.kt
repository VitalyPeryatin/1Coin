package com.finance_tracker.finance_tracker.data.broadcast_receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import com.finance_tracker.finance_tracker.data.broadcast_receivers.parsers.SmsMessageParser
import com.finance_tracker.finance_tracker.data.broadcast_receivers.parsers.templates.tinkoffSmsTemplate
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.financetracker.financetracker.data.SmsMessageEntityQueries
import com.financetracker.financetracker.data.TransactionsEntityQueries
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Date

class SmsBroadcastReceiver : BroadcastReceiver(), KoinComponent {

    private val transactionsEntityQueries: TransactionsEntityQueries by inject()
    private val smsMessageEntityQueries: SmsMessageEntityQueries by inject()

    private val smsMessageParser = SmsMessageParser(
        templates = listOf(tinkoffSmsTemplate)
    )

    override fun onReceive(context: Context?, intent: Intent?) {
        if (!intent?.action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) return
        val extractMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        extractMessages.forEach { smsMessage ->
            val transaction = smsMessageParser.parseMessage(smsMessage)

            if (transaction != null) {
                insertTransactionIntoDatabase(transaction)
            }
            insertSmsMessageIntoDatabase(
                smsMessage = smsMessage,
                hasTransaction = transaction != null
            )
        }
    }

    private fun insertTransactionIntoDatabase(transaction: Transaction) {
        transactionsEntityQueries.insertTransaction(
            id = null,
            type = transaction.type,
            amount = transaction.amount,
            amountCurrency = transaction.amountCurrency.name,
            categoryId = transaction.category?.id,
            accountId = transaction.account.id,
            insertionDate = Date(),
            date = transaction.date
        )
    }

    private fun insertSmsMessageIntoDatabase(smsMessage: SmsMessage, hasTransaction: Boolean) {
        smsMessageEntityQueries.insertSmsMessage(
            id = null,
            sender = smsMessage.displayOriginatingAddress.orEmpty(),
            message = smsMessage.displayMessageBody.orEmpty(),
            date = smsMessage.timestampMillis,
            isRecognized = hasTransaction
        )
    }
}