package com.example.calorietracker.features.tracking.ui.viewmodel

import com.example.calorietracker.features.tracking.domain.mapper.TrackingStateMapper
import com.example.calorietracker.features.tracking.domain.model.ProgressData
import com.example.calorietracker.features.tracking.domain.usecase.AddCaloriesUseCase
import com.example.calorietracker.features.tracking.domain.usecase.GetFormattedDateUseCase
import com.example.calorietracker.features.tracking.domain.usecase.ObserveProgressUseCase
import com.example.calorietracker.features.tracking.domain.usecase.ResetTodayCaloriesUseCase
import com.example.calorietracker.features.tracking.ui.state.TrackingState
import io.mockative.coVerify
import io.mockative.every
import io.mockative.mock
import io.mockative.of
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class TrackingViewModelTest {

    private val trackingStateMapper = mock(of<TrackingStateMapper>())
    private val addCaloriesUseCase = mock(of<AddCaloriesUseCase>())
    private val observeProgressUseCase = mock(of<ObserveProgressUseCase>())
    private val resetTodayCaloriesUseCase = mock(of<ResetTodayCaloriesUseCase>())
    private val getFormattedDateUseCase = mock(of<GetFormattedDateUseCase>())

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: TrackingViewModel

    private fun createViewModel() = TrackingViewModel(
        trackingStateMapper = trackingStateMapper,
        addCaloriesUseCase = addCaloriesUseCase,
        observeProgressUseCase = observeProgressUseCase,
        resetTodayCaloriesUseCase = resetTodayCaloriesUseCase,
        getFormattedDateUseCase = getFormattedDateUseCase
    )

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { observeProgressUseCase() } returns emptyFlow()
    }

    @Test
    fun `initializes with default tracking state`() = runTest(testDispatcher) {
        // WHEN
        viewModel = createViewModel()

        // THEN
        assertEquals(TrackingState(), viewModel.state.value)
    }

    @Test
    fun `updates state when progress emits data`() = runTest(testDispatcher) {
        // GIVEN
        val progress = ProgressData(total = 500, goal = 2500)
        val progressFlow = MutableStateFlow(progress)
        val expectedState = TrackingState(calories = 500, dateText = "Monday, January 15")
        every { observeProgressUseCase() } returns progressFlow
        every { getFormattedDateUseCase() } returns "Monday, January 15"
        every { trackingStateMapper.map(progress, "Monday, January 15") } returns expectedState

        // WHEN
        viewModel = createViewModel()
        advanceUntilIdle()

        // THEN
        assertEquals(expectedState, viewModel.state.value)
    }

    @Test
    fun `addCalories calls use case`() = runTest(testDispatcher) {
        // GIVEN
        viewModel = createViewModel()

        // WHEN
        viewModel.addCalories(500)
        advanceUntilIdle()

        // THEN
        coVerify { addCaloriesUseCase(500) }.wasInvoked()
    }

    @Test
    fun `resetCalories calls use case`() = runTest(testDispatcher) {
        // GIVEN
        viewModel = createViewModel()

        // WHEN
        viewModel.resetCalories()
        advanceUntilIdle()

        // THEN
        coVerify { resetTodayCaloriesUseCase() }.wasInvoked()
    }
}
