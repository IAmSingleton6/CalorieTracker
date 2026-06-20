package com.example.calorietracker.features.tracking.domain.usecase

import com.example.calorietracker.core.common.TimeProvider
import com.example.calorietracker.features.settings.domain.repository.SettingsRepository
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

class ObserveProgressUseCaseImplTest {

    private val calorieRepository = mock(of<CalorieRepository>())
    private val settingsRepository = mock(of<SettingsRepository>())
    private val timeProvider = mock(of<TimeProvider>())
    private val useCase = ObserveProgressUseCaseImpl(calorieRepository, settingsRepository, timeProvider)

    @Test
    fun `emits progress combining total and goal flows`() = runTest {
        // GIVEN
        val date = LocalDate(2024, 1, 15)
        every { timeProvider.todayDate() } returns date
        every { calorieRepository.observeTodayTotal(date) } returns flowOf(500)
        every { settingsRepository.observeGoal() } returns flowOf(2500)

        // WHEN
        val progress = useCase().first()

        // THEN
        assertEquals(500, progress.total)
        assertEquals(2500, progress.goal)
    }
}
