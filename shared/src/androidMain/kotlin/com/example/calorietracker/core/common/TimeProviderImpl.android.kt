package com.example.calorietracker.core.common

import kotlin.time.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class TimeProviderImpl : TimeProvider {
    override fun currentTimeMillis(): Long = System.currentTimeMillis()

    override fun todayDate(): LocalDate {
        val instant = Instant.fromEpochMilliseconds(System.currentTimeMillis())
        val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return dateTime.date
    }

    override fun nowInstant(): Instant = Instant.fromEpochMilliseconds(System.currentTimeMillis())

    override fun currentTimeZone(): TimeZone = TimeZone.currentSystemDefault()
}
