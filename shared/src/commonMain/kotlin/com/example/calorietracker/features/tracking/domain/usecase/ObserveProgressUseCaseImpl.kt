package com.example.calorietracker.features.tracking.domain.usecase

import com.example.calorietracker.core.common.TimeProvider
import com.example.calorietracker.features.tracking.domain.model.ProgressData
import com.example.calorietracker.features.settings.domain.repository.SettingsRepository
import com.example.calorietracker.features.tracking.domain.repository.CalorieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

internal class ObserveProgressUseCaseImpl(
    private val calorieRepository: CalorieRepository,
    private val settingsRepository: SettingsRepository,
    private val timeProvider: TimeProvider
) : ObserveProgressUseCase {

    override operator fun invoke(): Flow<ProgressData> {
        return combine(
            calorieRepository.observeTodayTotal(timeProvider.todayDate()),
            settingsRepository.observeGoal()
        ) { total, goal ->
            ProgressData(total = total, goal = goal)
        }
    }
}
