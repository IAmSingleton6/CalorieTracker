package com.example.calorietracker.features.settings.domain.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.calories.database.CalorieDatabase
import com.example.calorietracker.core.common.Defaults
import com.example.calorietracker.features.settings.domain.model.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SettingsRepositoryImpl(
    database: CalorieDatabase
) : SettingsRepository {

    private val queries = database.calorieTrackerDbQueries

    override fun observeSettings(): Flow<UserPreferences> {
        return queries.getPreferences()
            .asFlow()
            .mapToOneOrNull(Dispatchers.Default)
            .map { row ->
                row?.let {
                    UserPreferences(dailyGoal = it.daily_goal.toInt())
                } ?: UserPreferences(dailyGoal = Defaults.DAILY_GOAL)
            }
    }

    override fun observeGoal(): Flow<Int> {
        return observeSettings().map { it.dailyGoal }
    }

    override suspend fun getSettings(): UserPreferences {
        val row = queries.getPreferences().executeAsOneOrNull()
        return row?.let {
            UserPreferences(
                dailyGoal = it.daily_goal.toInt()
            )
        } ?: UserPreferences(dailyGoal = Defaults.DAILY_GOAL)
    }

    override suspend fun updateGoal(goal: Int) {
        queries.upsertPreferences(daily_goal = goal.toLong())
    }
}
