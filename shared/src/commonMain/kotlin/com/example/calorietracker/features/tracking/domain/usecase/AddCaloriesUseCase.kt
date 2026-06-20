package com.example.calorietracker.features.tracking.domain.usecase

interface AddCaloriesUseCase {
    suspend operator fun invoke(amount: Int)
}
