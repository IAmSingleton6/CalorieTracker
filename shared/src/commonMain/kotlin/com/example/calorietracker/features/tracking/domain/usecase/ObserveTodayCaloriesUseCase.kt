package com.example.calorietracker.features.tracking.domain.usecase

import kotlinx.coroutines.flow.Flow

interface ObserveTodayCaloriesUseCase {
    operator fun invoke(): Flow<Int>
}
