package com.finance_tracker.finance_tracker.core.analytics

import com.finance_tracker.finance_tracker.core.common.Context

interface AnalyticsTracker {

    fun init(context: Context)

    fun setUserId(userId: String)

    fun track(event: AnalyticsEvent)

    companion object {
        const val AMPLITUDE_API_KEY = "53a89fb85c5895ac9fb42631eacd28c2"
        const val ANONYM_USER_ID = "anonym"
    }
}
