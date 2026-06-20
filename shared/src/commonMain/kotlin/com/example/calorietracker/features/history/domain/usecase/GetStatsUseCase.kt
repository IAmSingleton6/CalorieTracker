package com.example.calorietracker.features.history.domain.usecase

import io.mockative.Mockable

@Mockable
interface GetStatsUseCase {
    suspend operator fun invoke(days: Int): Pair<Int, Float>
}
