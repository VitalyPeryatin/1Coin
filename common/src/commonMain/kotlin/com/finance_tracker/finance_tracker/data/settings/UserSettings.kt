package com.finance_tracker.finance_tracker.data.settings

import com.russhwolf.settings.Settings

@Suppress("RedundantSuspendModifier")
class UserSettings(factory: Settings.Factory) {

    private val settings = factory.create("user")

    suspend fun saveUserId(userId: String) {
        settings.putString(KEY_USER_ID, userId)
    }

    suspend fun getUserId(): String? {
        return settings.getStringOrNull(KEY_USER_ID)
    }

    companion object {
        private const val KEY_USER_ID = "user_id"
    }
}