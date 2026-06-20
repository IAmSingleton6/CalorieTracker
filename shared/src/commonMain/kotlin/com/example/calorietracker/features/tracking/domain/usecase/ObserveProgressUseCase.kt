package com.example.calorietracker.features.tracking.domain.usecase

import com.example.calorietracker.features.tracking.domain.model.ProgressData
import kotlinx.coroutines.flow.Flow

interface ObserveProgressUseCase {
    operator fun invoke(): Flow<ProgressData>
}
