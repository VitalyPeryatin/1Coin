package com.finance_tracker.finance_tracker.data.repositories

import com.finance_tracker.finance_tracker.data.settings.UserSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
    private val userSettings: UserSettings
) {

    suspend fun saveUserId(userId: String) {
        withContext(Dispatchers.IO) {
            userSettings.saveUserId(userId)
        }
    }

    suspend fun getUserId(): String? {
        return withContext(Dispatchers.IO) {
            userSettings.getUserId()
        }
    }
}