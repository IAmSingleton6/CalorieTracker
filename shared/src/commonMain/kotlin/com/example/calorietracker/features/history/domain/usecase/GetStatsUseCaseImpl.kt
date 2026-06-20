package com.example.calorietracker.features.history.domain.usecase

import com.example.calorietracker.core.common.TimeProvider
import com.example.calorietracker.features.settings.domain.repository.SettingsRepository
import com.example.calorietracker.features.tracking.domain.repository.CalorieRepository
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.minus

internal class GetStatsUseCaseImpl(
    private val calorieRepository: CalorieRepository,
    private val settingsRepository: SettingsRepository,
    private val timeProvider: TimeProvider
) : GetStatsUseCase {

    override suspend fun invoke(days: Int): Pair<Int, Float> {
        val today = timeProvider.todayDate()
        val startDate = today.minus(DatePeriod(days = days))
        val goal = settingsRepository.getSettings().dailyGoal
        val summaries = calorieRepository.getHistoryRange(startDate, today, goal)

        if (summaries.isEmpty()) return Pair(0, 0f)

        val average = summaries.sumOf { it.progressData.total } / summaries.size
        val metGoal = summaries.count { it.progressData.total <= goal }
        val adherence = metGoal.toFloat() / summaries.size * 100f

        return Pair(average, adherence)
    }
}
