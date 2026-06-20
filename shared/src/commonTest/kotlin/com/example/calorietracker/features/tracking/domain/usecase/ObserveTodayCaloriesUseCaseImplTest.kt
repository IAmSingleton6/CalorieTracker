package com.example.calorietracker.features.tracking.domain.usecase

import com.example.calorietracker.core.common.TimeProvider
import com.example.calorietracker.features.tracking.domain.repository.CalorieRepository
import io.mockative.every
import io.mockative.mock
import io.mockative.of
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals

class ObserveTodayCaloriesUseCaseImplTest {

    private val repository = mock(of<CalorieRepository>())
    private val timeProvider = mock(of<TimeProvider>())
    private val useCase = ObserveTodayCaloriesUseCaseImpl(repository, timeProvider)

    @Test
    fun `returns flow from repository for today date`() = runTest {
        // GIVEN
        val date = LocalDate(2024, 1, 15)
        every { timeProvider.todayDate() } returns date
        every { repository.observeTodayTotal(date) } returns flowOf(500)

        // WHEN
        val result = useCase()

        // THEN
        assertEquals(500, result.first())
    }
}
