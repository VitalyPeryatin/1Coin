package com.finance_tracker.finance_tracker.core.analytics

import org.koin.java.KoinJavaComponent

abstract class BaseAnalytics {

    private val analyticsTracker: AnalyticsTracker by lazy { KoinJavaComponent.getKoin().get() }
    protected abstract val screenName: String
    private val isAnalyticsEnabled = true

    fun trackClick(
        eventName: String,
        properties: Map<String, Any> = mapOf()
    ) {
        if (!isAnalyticsEnabled) return

        analyticsTracker.track(
            AnalyticsEvent.Click(
                source = screenName,
                clickName = eventName,
                properties = properties
            )
        )
    }

    fun trackScreenOpen() {
        if (!isAnalyticsEnabled) return

        analyticsTracker.track(
            AnalyticsEvent.Open(
                screenName = screenName
            )
        )
    }
}