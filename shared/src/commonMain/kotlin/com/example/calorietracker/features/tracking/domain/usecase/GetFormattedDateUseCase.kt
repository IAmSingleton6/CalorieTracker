package com.example.calorietracker.features.tracking.domain.usecase

import io.mockative.Mockable

@Mockable
interface GetFormattedDateUseCase {
    operator fun invoke(): String
}
