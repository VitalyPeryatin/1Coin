package com.finance_tracker.finance_tracker.data.broadcast_receivers.parsers.models


data class SmsTemplate(
    val fromAddresses: List<String>,
    val pattern: String
) {
    val regex = getRegexFromPattern(pattern)
    val transactionFields = getAllTransactionFieldsFromPattern(pattern)

    companion object {

        private fun getRegexFromPattern(pattern: String): Regex {
            var regexString = pattern
            for (templateField in TransactionField.values()) {
                regexString = regexString.replaceFirst(
                    oldValue = templateField.template,
                    newValue = templateField.regex
                )
            }
            return regexString.toRegex()
        }

        private fun getAllTransactionFieldsFromPattern(pattern: String): List<TransactionField> {
            return TransactionField.values().asSequence()
                .map { Pair(it, pattern.indexOf(it.template)) }
                .filter { it.second >= 0 }
                .sortedBy { it.second }
                .map { it.first }
                .toList()
        }
    }
}