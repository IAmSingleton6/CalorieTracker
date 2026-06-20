package com.example.calorietracker.features.settings.domain.repository

import com.example.calorietracker.features.settings.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun observeSettings(): Flow<UserPreferences>
    fun observeGoal(): Flow<Int>
    suspend fun getSettings(): UserPreferences
    suspend fun updateGoal(goal: Int)
}
