package com.example.calorietracker.features.settings.ui.viewmodel

import com.example.calorietracker.features.settings.domain.model.UserPreferences
import com.example.calorietracker.features.settings.domain.repository.SettingsRepository
import com.example.calorietracker.features.tracking.domain.repository.CalorieRepository
import io.mockative.any
import io.mockative.coVerify
import io.mockative.every
import io.mockative.mock
import io.mockative.of
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    private val settingsRepository = mock(of<SettingsRepository>())
    private val calorieRepository = mock(of<CalorieRepository>())

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: SettingsViewModel

    private fun createViewModel() = SettingsViewModel(settingsRepository, calorieRepository)

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { settingsRepository.observeSettings() } returns MutableStateFlow(UserPreferences(2500))
    }

    @Test
    fun `initializes with settings from repository`() = runTest(testDispatcher) {
        // GIVEN
        every { settingsRepository.observeSettings() } returns MutableStateFlow(UserPreferences(dailyGoal = 2000))

        // WHEN
        viewModel = createViewModel()
        advanceUntilIdle()

        // THEN
        assertEquals(2000, viewModel.state.value.dailyGoal)
        assertEquals("2000", viewModel.state.value.goalInput)
    }

    @Test
    fun `updateGoalInput updates goal input in state`() = runTest(testDispatcher) {
        // GIVEN
        viewModel = createViewModel()
        advanceUntilIdle()

        // WHEN
        viewModel.updateGoalInput("2000")

        // THEN
        assertEquals("2000", viewModel.state.value.goalInput)
    }

    @Test
    fun `saveGoal with valid positive input updates goal`() = runTest(testDispatcher) {
        // GIVEN
        viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.updateGoalInput("2000")

        // WHEN
        viewModel.saveGoal()
        advanceUntilIdle()

        // THEN
        coVerify { settingsRepository.updateGoal(2000) }.wasInvoked()
    }

    @Test
    fun `saveGoal with non numeric input does not update goal`() = runTest(testDispatcher) {
        // GIVEN
        viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.updateGoalInput("invalid")

        // WHEN
        viewModel.saveGoal()
        advanceUntilIdle()

        // THEN
        coVerify { settingsRepository.updateGoal(any()) }.wasNotInvoked()
    }

    @Test
    fun `saveGoal with zero input does not update goal`() = runTest(testDispatcher) {
        // GIVEN
        viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.updateGoalInput("0")

        // WHEN
        viewModel.saveGoal()
        advanceUntilIdle()

        // THEN
        coVerify { settingsRepository.updateGoal(any()) }.wasNotInvoked()
    }

    @Test
    fun `showResetConfirmation sets flag to true`() = runTest(testDispatcher) {
        // GIVEN
        viewModel = createViewModel()
        advanceUntilIdle()

        // WHEN
        viewModel.showResetConfirmation()

        // THEN
        assertTrue(viewModel.state.value.showResetConfirmation)
    }

    @Test
    fun `dismissResetConfirmation sets flag to false`() = runTest(testDispatcher) {
        // GIVEN
        viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.showResetConfirmation()

        // WHEN
        viewModel.dismissResetConfirmation()

        // THEN
        assertFalse(viewModel.state.value.showResetConfirmation)
    }

    @Test
    fun `resetAllData deletes entries and dismisses confirmation`() = runTest(testDispatcher) {
        // GIVEN
        viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.showResetConfirmation()

        // WHEN
        viewModel.resetAllData()
        advanceUntilIdle()

        // THEN
        coVerify { calorieRepository.deleteAllEntries() }.wasInvoked()
        assertFalse(viewModel.state.value.showResetConfirmation)
    }
}
