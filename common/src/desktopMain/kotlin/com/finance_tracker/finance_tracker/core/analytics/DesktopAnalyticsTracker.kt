package com.finance_tracker.finance_tracker.core.analytics

import com.amplitude.Amplitude
import com.amplitude.AmplitudeLog
import com.amplitude.Event
import com.finance_tracker.finance_tracker.core.common.DesktopContext
import com.finance_tracker.finance_tracker.core.common.runSafeCatching
import com.finance_tracker.finance_tracker.data.settings.AnalyticsSettings
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext

class DesktopAnalyticsTracker(
    analyticsSettings: AnalyticsSettings
): AnalyticsTracker, CoroutineScope {

    private val amplitude = Amplitude.getInstance()
    private var userId = AnalyticsTracker.ANONYM_USER_ID

    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.IO
    private val isAnalyticsEnabledFlow: StateFlow<Boolean> = analyticsSettings.isAnalyticsEnabledFlow()
        .stateIn(this, SharingStarted.Lazily, initialValue = false)

    private val isAnalyticsDisabled: Boolean
        get() = !isAnalyticsEnabledFlow.value

    override fun init(context: DesktopContext) {
        amplitude.init(AnalyticsTracker.AMPLITUDE_API_KEY)
        amplitude.setLogMode(AmplitudeLog.LogMode.DEBUG)
    }

    override fun setUserProperty(property: String, value: Any) {
        if (isAnalyticsDisabled) return

        val desktopEvent = Event("UserPropertiesEvent", userId)

        val userProperties = JSONObject()
        desktopEvent.userProperties = userProperties
        desktopEvent.platform  = "Desktop"

        amplitude.logEvent(desktopEvent)
        logUserProperties(userProperties)
    }

    override fun setUserId(userId: String) {
        this.userId = userId
    }

    override fun track(event: AnalyticsEvent) {
        if (isAnalyticsDisabled) return

        val desktopEvent = Event(event.name, userId)

        val eventProps = JSONObject()
        runSafeCatching {
            event.properties.forEach(eventProps::put)
        }

        desktopEvent.eventProperties = eventProps
        desktopEvent.platform  = "Desktop"

        amplitude.logEvent(desktopEvent)
        log(event)
    }

    private fun log(event: AnalyticsEvent) {
        var message = "${event.name}. "
        if (event.properties.isNotEmpty()) {
            message += "properties: ${event.properties}"
        }
        Napier.d(message = message, tag = "Amplitude")
    }

    private fun logUserProperties(userProperties: JSONObject) {
        val message = "UserProperties: $userProperties"
        Napier.d(message = message, tag = "Amplitude")
    }
}