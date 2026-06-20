package com.example.calorietracker.features.tracking.domain.usecase

import io.mockative.Mockable
import kotlinx.coroutines.flow.Flow

@Mockable
interface ObserveTodayCaloriesUseCase {
    operator fun invoke(): Flow<Int>
}
