package com.example.calorietracker.features.history.ui.viewmodel

import com.example.calorietracker.core.model.DailySummary
import com.example.calorietracker.features.history.domain.model.HistoryPeriod
import com.example.calorietracker.features.history.domain.usecase.GetHistoryUseCase
import com.example.calorietracker.features.history.domain.usecase.GetStatsUseCase
import com.example.calorietracker.features.tracking.domain.model.ProgressData
import io.mockative.coEvery
import io.mockative.coVerify
import io.mockative.mock
import io.mockative.of
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class HistoryViewModelTest {

    private val getHistoryUseCase = mock(of<GetHistoryUseCase>())
    private val getStatsUseCase = mock(of<GetStatsUseCase>())

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: HistoryViewModel

    private fun createViewModel() = HistoryViewModel(getHistoryUseCase, getStatsUseCase)

    @BeforeTest
    fun setup() = runTest(testDispatcher) {
        Dispatchers.setMain(testDispatcher)

        coEvery { getHistoryUseCase(7) } returns emptyList()
        coEvery { getStatsUseCase(7) } returns Pair(0, 0f)
    }

    @Test
    fun `loads history on init with Week period`() = runTest(testDispatcher) {
        // GIVEN
        val summaries = listOf(
            DailySummary(LocalDate(2024, 1, 8), ProgressData(2000, 2500)),
            DailySummary(LocalDate(2024, 1, 9), ProgressData(2500, 2500))
        )
        coEvery { getHistoryUseCase(7) } returns summaries
        coEvery { getStatsUseCase(7) } returns Pair(2250, 100f)

        // WHEN
        viewModel = createViewModel()
        advanceUntilIdle()

        // THEN
        assertEquals(HistoryPeriod.Week, viewModel.state.value.period)
        assertEquals(summaries, viewModel.state.value.summaries)
        assertEquals(2250, viewModel.state.value.average)
        assertEquals(100f, viewModel.state.value.adherencePercent)
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `loadHistory sets loading state`() = runTest(testDispatcher) {
        // WHEN
        viewModel = createViewModel()

        // THEN
        assertTrue(viewModel.state.value.isLoading)

        // WHEN
        advanceUntilIdle()

        // THEN
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `setPeriod updates period and reloads with new days`() = runTest(testDispatcher) {
        // GIVEN
        val monthSummaries = listOf(
            DailySummary(LocalDate(2024, 1, 1), ProgressData(2000, 2500))
        )
        coEvery { getHistoryUseCase(30) } returns monthSummaries
        coEvery { getStatsUseCase(30) } returns Pair(2000, 100f)
        viewModel = createViewModel()
        advanceUntilIdle()

        // WHEN
        viewModel.setPeriod(HistoryPeriod.Month)
        advanceUntilIdle()

        // THEN
        assertEquals(HistoryPeriod.Month, viewModel.state.value.period)
        assertEquals(monthSummaries, viewModel.state.value.summaries)
        coVerify { getHistoryUseCase(30) }.wasInvoked()
        coVerify { getStatsUseCase(30) }.wasInvoked()
    }

    @Test
    fun `returns empty state when use cases return no data`() = runTest(testDispatcher) {
        // WHEN
        viewModel = createViewModel()
        advanceUntilIdle()

        // THEN
        assertEquals(emptyList<DailySummary>(), viewModel.state.value.summaries)
        assertEquals(0, viewModel.state.value.average)
        assertEquals(0f, viewModel.state.value.adherencePercent)
        assertFalse(viewModel.state.value.isLoading)
    }
}
