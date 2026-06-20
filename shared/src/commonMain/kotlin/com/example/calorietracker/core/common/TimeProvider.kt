package com.example.calorietracker.core.common

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlin.time.Instant

interface TimeProvider {
    fun currentTimeMillis(): Long
    fun todayDate(): LocalDate
    fun nowInstant(): Instant
    fun currentTimeZone(): TimeZone
}
