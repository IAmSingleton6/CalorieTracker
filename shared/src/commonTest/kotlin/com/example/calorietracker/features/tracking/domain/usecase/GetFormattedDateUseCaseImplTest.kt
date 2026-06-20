package com.example.calorietracker.features.tracking.domain.usecase

import com.example.calorietracker.core.common.TimeProvider
import io.mockative.every
import io.mockative.mock
import io.mockative.of
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.TimeZone
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Instant

class GetFormattedDateUseCaseImplTest {

    private val timeProvider = mock(of<TimeProvider>())
    private val useCase = GetFormattedDateUseCaseImpl(timeProvider)

    @BeforeTest
    fun setup() {
        every { timeProvider.currentTimeZone() } returns TimeZone.UTC
    }

    @Test
    fun `formats Monday January 15`() = runTest {
        // GIVEN
        val instant = Instant.parse("2024-01-15T12:00:00Z")
        every { timeProvider.nowInstant() } returns instant

        // WHEN
        val result = useCase()

        // THEN
        assertEquals("Monday, January 15", result)
    }

    @Test
    fun `formats Tuesday February 13`() = runTest {
        // GIVEN
        val instant = Instant.parse("2024-02-13T12:00:00Z")
        every { timeProvider.nowInstant() } returns instant

        // WHEN
        val result = useCase()

        // THEN
        assertEquals("Tuesday, February 13", result)
    }

    @Test
    fun `formats Thursday July 4`() = runTest {
        // GIVEN
        val instant = Instant.parse("2024-07-04T12:00:00Z")
        every { timeProvider.nowInstant() } returns instant

        // WHEN
        val result = useCase()

        // THEN
        assertEquals("Thursday, July 4", result)
    }

    @Test
    fun `formats Wednesday December 25`() = runTest {
        // GIVEN
        val instant = Instant.parse("2024-12-25T12:00:00Z")
        every { timeProvider.nowInstant() } returns instant

        // WHEN
        val result = useCase()

        // THEN
        assertEquals("Wednesday, December 25", result)
    }
}
