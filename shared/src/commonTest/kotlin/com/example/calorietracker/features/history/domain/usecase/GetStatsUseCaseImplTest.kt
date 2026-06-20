package com.example.calorietracker.features.history.domain.usecase

import com.example.calorietracker.core.common.TimeProvider
import com.example.calorietracker.core.model.DailySummary
import com.example.calorietracker.features.settings.domain.model.UserPreferences
import com.example.calorietracker.features.settings.domain.repository.SettingsRepository
import com.example.calorietracker.features.tracking.domain.model.ProgressData
import com.example.calorietracker.features.tracking.domain.repository.CalorieRepository
import io.mockative.coEvery
import io.mockative.every
import io.mockative.mock
import io.mockative.of
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetStatsUseCaseImplTest {

    private val calorieRepository = mock(of<CalorieRepository>())
    private val settingsRepository = mock(of<SettingsRepository>())
    private val timeProvider = mock(of<TimeProvider>())
    private val useCase = GetStatsUseCaseImpl(calorieRepository, settingsRepository, timeProvider)

    @BeforeTest
    fun setup() = runBlocking {
        every { timeProvider.todayDate() } returns today
        coEvery { settingsRepository.getSettings() } returns UserPreferences(goal)
    }

    @Test
    fun `calculates average and adherence percentage`() = runTest {
        // GIVEN
        val summaries = listOf(
            DailySummary(startDate8, ProgressData(2000, goal)),
            DailySummary(LocalDate(2024, 1, 9), ProgressData(3000, goal))
        )
        coEvery { calorieRepository.getHistoryRange(startDate8, today, goal) } returns summaries

        // WHEN
        val (average, adherence) = useCase(7)

        // THEN
        assertEquals(2500, average)
        assertEquals(50f, adherence)
    }

    @Test
    fun `returns zero average and adherence when history is empty`() = runTest {
        // GIVEN
        coEvery { settingsRepository.getSettings() } returns UserPreferences(goal)
        coEvery { calorieRepository.getHistoryRange(startDate8, today, goal) } returns emptyList()

        // WHEN
        val (average, adherence) = useCase(7)

        // THEN
        assertEquals(0, average)
        assertEquals(0f, adherence)
    }

    @Test
    fun `calculates adherence correctly when all days meet goal`() = runTest {
        // GIVEN
        val summaries = listOf(
            DailySummary(startDate13, ProgressData(2000, goal)),
            DailySummary(LocalDate(2024, 1, 14), ProgressData(2500, goal))
        )
        coEvery { calorieRepository.getHistoryRange(startDate13, today, goal) } returns summaries

        // WHEN
        val (_, adherence) = useCase(2)

        // THEN
        assertEquals(100f, adherence)
    }

    @Test
    fun `calculates adherence correctly when no days meet goal`() = runTest {
        // GIVEN
        val summaries = listOf(
            DailySummary(startDate13, ProgressData(3000, goal)),
            DailySummary(LocalDate(2024, 1, 14), ProgressData(2600, goal))
        )
        coEvery { calorieRepository.getHistoryRange(startDate13, today, goal) } returns summaries

        // WHEN
        val (_, adherence) = useCase(2)

        // THEN
        assertEquals(0f, adherence)
    }

    companion object {
        private val today = LocalDate(2024, 1, 15)
        private val startDate8 = LocalDate(2024, 1, 8)
        private val startDate13 = LocalDate(2024, 1, 13)
        private val goal = 2500
    }
}
