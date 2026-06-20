package com.example.calorietracker.features.settings.domain.repository

import com.example.calorietracker.features.settings.domain.model.UserPreferences
import io.mockative.Mockable
import kotlinx.coroutines.flow.Flow

@Mockable
interface SettingsRepository {
    fun observeSettings(): Flow<UserPreferences>
    fun observeGoal(): Flow<Int>
    suspend fun getSettings(): UserPreferences
    suspend fun updateGoal(goal: Int)
}
