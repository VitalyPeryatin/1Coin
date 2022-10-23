package com.finance_tracker.finance_tracker.data.broadcast_receivers.parsers.models

enum class TransactionField(
    val template: String,
    val regex: String
) {
    Type(
        template = "{{Type}}",
        regex = "([A-Яa-я]+)"
    ),
    AmountCurrency(
        template = "{{AmountCurrency}}",
        regex = "([A-Z]+)"
    ),
    Amount(
        template = "{{Amount}}",
        regex = "([0-9.]+)"
    ),
    CardNumber(
        template = "{{CardNumber}}",
        regex = "([*0-9]+)"
    ),
    Place(
        template = "{{Place}}",
        regex = "([A-Za-zА-Яа-я .]+)"
    )
}