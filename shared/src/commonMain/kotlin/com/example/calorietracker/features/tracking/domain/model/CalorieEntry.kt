package com.example.calorietracker.features.tracking.domain.model

import kotlin.time.Instant
import kotlinx.datetime.LocalDate

data class CalorieEntry(
    val id: Long,
    val amount: Int,
    val date: LocalDate,
    val createdAt: Instant
)
