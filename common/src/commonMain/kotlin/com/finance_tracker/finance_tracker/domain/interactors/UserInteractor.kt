package com.finance_tracker.finance_tracker.domain.interactors

import com.benasher44.uuid.Uuid
import com.finance_tracker.finance_tracker.data.repositories.UserRepository

class UserInteractor(
    private val userRepository: UserRepository
) {

    suspend fun getOrCreateUserId(): String {
        val currentUserId = userRepository.getUserId()
        if (currentUserId != null) return currentUserId

        val newUserId = Uuid.randomUUID().toString()
        userRepository.saveUserId(newUserId)
        return newUserId
    }
}