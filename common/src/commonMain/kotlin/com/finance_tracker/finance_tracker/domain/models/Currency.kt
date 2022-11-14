package com.finance_tracker.finance_tracker.domain.models

data class Currency(
    val name: String,
    val sign: String
) {
    companion object {

        private val USD = Currency(name = "USD", sign = "\$")
        private val EUR = Currency(name = "EUR", sign = "€")
        private val RUB = Currency(name = "RUB", sign = "₽")
        private val TRY = Currency(name = "TRY", sign = "₺")

        val list = listOf(USD, EUR, RUB, TRY)
        val default = USD

        private val map = list.associateBy { it.name }

        fun getByName(name: String): Currency {
            return map[name]!!
        }
    }
}