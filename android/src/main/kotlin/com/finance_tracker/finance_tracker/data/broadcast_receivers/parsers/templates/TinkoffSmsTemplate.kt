package com.finance_tracker.finance_tracker.data.broadcast_receivers.parsers.templates

import com.finance_tracker.finance_tracker.data.broadcast_receivers.parsers.models.SmsTemplate
import com.finance_tracker.finance_tracker.data.broadcast_receivers.parsers.models.TransactionField

val tinkoffSmsTemplate = SmsTemplate(
    fromAddresses = listOf("Tinkoff"),
    pattern = "${TransactionField.Type.template}, " +
            "карта ${TransactionField.CardNumber.template}. " +
            "${TransactionField.Amount.template} ${TransactionField.AmountCurrency.template}. " +
            "${TransactionField.Place.template}. Доступно .*"
)