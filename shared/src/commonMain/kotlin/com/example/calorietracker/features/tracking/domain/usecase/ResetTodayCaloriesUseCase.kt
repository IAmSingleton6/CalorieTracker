package com.example.calorietracker.features.tracking.domain.usecase

import io.mockative.Mockable

@Mockable
interface ResetTodayCaloriesUseCase {
    suspend operator fun invoke()
}
