package com.example.calorietracker.core.common

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlin.time.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import platform.posix.gettimeofday
import platform.posix.timeval

@OptIn(ExperimentalForeignApi::class)
class TimeProviderImpl : TimeProvider {
    override fun currentTimeMillis(): Long = memScoped {
        val tv = alloc<timeval>()
        gettimeofday(tv.ptr, null)
        tv.tv_sec * 1000L + tv.tv_usec / 1000L
    }

    override fun todayDate(): LocalDate {
        val instant = Instant.fromEpochMilliseconds(currentTimeMillis())
        val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return dateTime.date
    }

    override fun nowInstant(): Instant = Instant.fromEpochMilliseconds(currentTimeMillis())

    override fun currentTimeZone(): TimeZone = TimeZone.currentSystemDefault()
}
