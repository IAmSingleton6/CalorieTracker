package com.example.calorietracker.features.tracking.domain.usecase

import com.example.calorietracker.core.common.TimeProvider
import com.example.calorietracker.features.tracking.domain.repository.CalorieRepository

internal class ResetTodayCaloriesUseCaseImpl(
    private val repository: CalorieRepository,
    private val timeProvider: TimeProvider
) : ResetTodayCaloriesUseCase {

    override suspend fun invoke() {
        repository.deleteTodayEntries(timeProvider.todayDate())
    }
}
