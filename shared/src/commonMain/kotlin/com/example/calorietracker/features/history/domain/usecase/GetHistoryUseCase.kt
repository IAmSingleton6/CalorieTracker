package com.example.calorietracker.features.history.domain.usecase

import com.example.calorietracker.core.model.DailySummary

interface GetHistoryUseCase {
    suspend operator fun invoke(days: Int): List<DailySummary>
}
