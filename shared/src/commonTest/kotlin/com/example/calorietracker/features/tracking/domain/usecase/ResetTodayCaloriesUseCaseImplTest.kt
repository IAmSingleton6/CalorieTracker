package com.example.calorietracker.features.tracking.domain.usecase

import com.example.calorietracker.core.common.TimeProvider
import com.example.calorietracker.features.tracking.domain.repository.CalorieRepository
import io.mockative.coVerify
import io.mockative.every
import io.mockative.mock
import io.mockative.of
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.Test

class ResetTodayCaloriesUseCaseImplTest {

    private val repository = mock(of<CalorieRepository>())
    private val timeProvider = mock(of<TimeProvider>())
    private val useCase = ResetTodayCaloriesUseCaseImpl(repository, timeProvider)

    @Test
    fun `deletes today entries from repository`() = runTest {
        // GIVEN
        val date = LocalDate(2024, 1, 15)
        every { timeProvider.todayDate() } returns date

        // WHEN
        useCase()

        // THEN
        coVerify { repository.deleteTodayEntries(date) }.wasInvoked()
    }
}
