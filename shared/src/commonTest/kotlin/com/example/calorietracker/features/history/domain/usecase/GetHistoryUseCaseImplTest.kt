package com.example.calorietracker.features.history.domain.usecase

import com.example.calorietracker.core.common.TimeProvider
import com.example.calorietracker.core.model.DailySummary
import com.example.calorietracker.features.settings.domain.model.UserPreferences
import com.example.calorietracker.features.settings.domain.repository.SettingsRepository
import com.example.calorietracker.features.tracking.domain.model.ProgressData
import com.example.calorietracker.features.tracking.domain.repository.CalorieRepository
import io.mockative.coEvery
import io.mockative.coVerify
import io.mockative.every
import io.mockative.mock
import io.mockative.of
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetHistoryUseCaseImplTest {

    companion object {
        private val today = LocalDate(2024, 1, 15)
        private val startDate = LocalDate(2024, 1, 8)
        private val goal = 2500
    }

    private val calorieRepository = mock(of<CalorieRepository>())
    private val settingsRepository = mock(of<SettingsRepository>())
    private val timeProvider = mock(of<TimeProvider>())
    private val useCase = GetHistoryUseCaseImpl(calorieRepository, settingsRepository, timeProvider)

    @BeforeTest
    fun setup() {
        every { timeProvider.todayDate() } returns today
    }

    @Test
    fun `returns history summaries for given days`() = runTest {
        // GIVEN
        val summaries = listOf(
            DailySummary(startDate, ProgressData(2000, goal)),
            DailySummary(LocalDate(2024, 1, 9), ProgressData(2500, goal))
        )
        coEvery { settingsRepository.getSettings() } returns UserPreferences(goal)
        coEvery { calorieRepository.getHistoryRange(startDate, today, goal) } returns summaries

        // WHEN
        val result = useCase(7)

        // THEN
        assertEquals(summaries, result)
        coVerify { calorieRepository.getHistoryRange(startDate, today, goal) }.wasInvoked()
    }

    @Test
    fun `returns empty list when no history exists`() = runTest {
        // GIVEN
        coEvery { settingsRepository.getSettings() } returns UserPreferences(goal)
        coEvery { calorieRepository.getHistoryRange(startDate, today, goal) } returns emptyList()

        // WHEN
        val result = useCase(7)

        // THEN
        assertEquals(emptyList(), result)
    }
}
