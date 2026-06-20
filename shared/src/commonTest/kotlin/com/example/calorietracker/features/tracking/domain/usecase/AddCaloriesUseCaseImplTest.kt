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

class AddCaloriesUseCaseImplTest {

    private val repository = mock(of<CalorieRepository>())
    private val timeProvider = mock(of<TimeProvider>())
    private val useCase = AddCaloriesUseCaseImpl(repository, timeProvider)

    @Test
    fun `invokes addEntry on repository with amount and today date`() = runTest {
        // GIVEN
        val date = LocalDate(2024, 1, 15)
        every { timeProvider.todayDate() } returns date

        // WHEN
        useCase(500)

        // THEN
        coVerify { repository.addEntry(500, date) }.wasInvoked()
    }
}
