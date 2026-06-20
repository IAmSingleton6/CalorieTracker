package com.example.calorietracker.features.history.domain.usecase

import com.example.calorietracker.core.common.TimeProvider
import com.example.calorietracker.core.model.DailySummary
import com.example.calorietracker.features.settings.domain.repository.SettingsRepository
import com.example.calorietracker.features.tracking.domain.repository.CalorieRepository
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.minus

internal class GetHistoryUseCaseImpl(
    private val calorieRepository: CalorieRepository,
    private val settingsRepository: SettingsRepository,
    private val timeProvider: TimeProvider
) : GetHistoryUseCase {

    override suspend fun invoke(days: Int): List<DailySummary> {
        val today = timeProvider.todayDate()
        val startDate = today.minus(DatePeriod(days = days))
        val goal = settingsRepository.getSettings().dailyGoal
        return calorieRepository.getHistoryRange(startDate, today, goal)
    }
}
