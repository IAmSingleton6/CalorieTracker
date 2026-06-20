package com.example.calorietracker.core.common

import io.mockative.Mockable
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlin.time.Instant

@Mockable
interface TimeProvider {
    fun currentTimeMillis(): Long
    fun todayDate(): LocalDate
    fun nowInstant(): Instant
    fun currentTimeZone(): TimeZone
}
