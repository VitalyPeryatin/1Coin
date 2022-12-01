package com.finance_tracker.finance_tracker.core.analytics

sealed class AnalyticsEvent(
    val name: String,
    open val properties: Map<String, Any> = mapOf()
) {

    open class Open(
        screenName: String
    ) : AnalyticsEvent(
        name = "${screenName}_Open"
    )

    open class Click(
        source: String,
        val clickName: String,
        override val properties: Map<String, Any> = mapOf()
    ) : AnalyticsEvent(
        name = "${source}_${clickName}_Click"
    )
}