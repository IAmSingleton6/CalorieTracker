package com.example.calorietracker.features.tracking.domain.usecase

import io.mockative.Mockable

@Mockable
interface AddCaloriesUseCase {
    suspend operator fun invoke(amount: Int)
}
