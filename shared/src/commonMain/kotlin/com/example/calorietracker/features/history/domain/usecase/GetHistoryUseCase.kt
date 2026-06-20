package com.example.calorietracker.features.history.domain.usecase

import com.example.calorietracker.core.model.DailySummary
import io.mockative.Mockable

@Mockable
interface GetHistoryUseCase {
    suspend operator fun invoke(days: Int): List<DailySummary>
}
