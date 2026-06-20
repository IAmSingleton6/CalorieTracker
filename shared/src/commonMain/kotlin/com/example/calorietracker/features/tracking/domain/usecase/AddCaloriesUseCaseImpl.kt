package com.example.calorietracker.features.tracking.domain.usecase

import com.example.calorietracker.core.common.TimeProvider
import com.example.calorietracker.features.tracking.domain.repository.CalorieRepository

internal class AddCaloriesUseCaseImpl(
    private val repository: CalorieRepository,
    private val timeProvider: TimeProvider
) : AddCaloriesUseCase {

    override suspend fun invoke(amount: Int) {
        repository.addEntry(amount, timeProvider.todayDate())
    }
}
