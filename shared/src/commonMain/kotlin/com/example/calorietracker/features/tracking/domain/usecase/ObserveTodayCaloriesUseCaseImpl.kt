package com.example.calorietracker.features.tracking.domain.usecase

import com.example.calorietracker.core.common.TimeProvider
import com.example.calorietracker.features.tracking.domain.repository.CalorieRepository
import kotlinx.coroutines.flow.Flow

internal class ObserveTodayCaloriesUseCaseImpl(
    private val repository: CalorieRepository,
    private val timeProvider: TimeProvider
) : ObserveTodayCaloriesUseCase {

    override operator fun invoke(): Flow<Int> {
        return repository.observeTodayTotal(timeProvider.todayDate())
    }
}
