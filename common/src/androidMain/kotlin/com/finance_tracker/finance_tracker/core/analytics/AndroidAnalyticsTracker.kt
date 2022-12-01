package com.finance_tracker.finance_tracker.core.analytics

import com.amplitude.android.Amplitude
import com.amplitude.android.Configuration
import com.finance_tracker.finance_tracker.core.common.Context

class AndroidAnalyticsTracker: AnalyticsTracker {

    @Suppress("LateinitUsage")
    private lateinit var amplitude: Amplitude

    override fun init(context: Context) {
        amplitude = Amplitude(
            Configuration(
                apiKey = AnalyticsTracker.AMPLITUDE_API_KEY,
                context = context
            )
        ).apply {
            setUserId(AnalyticsTracker.ANONYM_USER_ID)
        }
    }

    override fun setUserId(userId: String) {
        amplitude.setUserId(userId)
    }

    override fun track(event: AnalyticsEvent) {
        amplitude.track(event.name, event.properties)
    }
}