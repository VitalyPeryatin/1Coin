package com.finance_tracker.finance_tracker.domain.models

data class Currency(
    val name: String,
    val sign: String
) {
    companion object {
        val list = listOf(
            Currency(name = "USD", sign = "\$"),
            Currency(name = "EURO", sign = "€"),
            Currency(name = "GBR", sign = "£")
        )

        private val map = list.associateBy { it.name }

        fun getByName(name: String): Currency {
            return map[name]!!
        }
    }
}