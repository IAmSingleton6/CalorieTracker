package com.example.calorietracker.core.model

import com.example.calorietracker.features.tracking.domain.model.ProgressData
import kotlinx.datetime.LocalDate

data class DailySummary(
    val date: LocalDate,
    val progressData: ProgressData,
)
