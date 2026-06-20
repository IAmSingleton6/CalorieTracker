package com.example.calorietracker.features.history.domain.usecase

interface GetStatsUseCase {
    suspend operator fun invoke(days: Int): Pair<Int, Float>
}
