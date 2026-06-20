package com.example.calorietracker.features.tracking.domain.usecase

import com.example.calorietracker.features.tracking.domain.model.ProgressData
import io.mockative.Mockable
import kotlinx.coroutines.flow.Flow

@Mockable
interface ObserveProgressUseCase {
    operator fun invoke(): Flow<ProgressData>
}
