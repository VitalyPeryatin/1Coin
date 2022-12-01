package com.finance_tracker.finance_tracker.core.common.settings

import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import java.util.prefs.Preferences

class DesktopSettingsFactory: Settings.Factory {

    override fun create(name: String?): Settings {
        val delegate = Preferences.userRoot().node(name)
        return PreferencesSettings(delegate)
    }
}