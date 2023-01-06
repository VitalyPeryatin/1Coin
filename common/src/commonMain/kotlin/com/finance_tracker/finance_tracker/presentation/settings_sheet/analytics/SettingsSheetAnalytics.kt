package com.finance_tracker.finance_tracker.presentation.settings_sheet.analytics

import com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics
import com.finance_tracker.finance_tracker.domain.models.Currency

class SettingsSheetAnalytics: BaseAnalytics() {

    override val screenName = "SettingsSheet"

    fun trackMainCurrencySelected(currency: Currency) {
        trackClick(
            eventName = "MainCurrencySelected",
            properties = mapOf(
                "currency" to currency.code
            )
        )
    }

    fun trackChooseCurrencyClick(currency: Currency) {
        trackClick(
            eventName = "ChooseMainCurrency",
            properties = mapOf(
                "currency" to currency.code
            )
        )
    }

    fun trackCategorySettingsClick() {
        trackClick(eventName = "CategorySettings")
    }

    fun trackTelegramCommunityClick() {
        trackClick(eventName = "TelegramCommunity")
    }
}