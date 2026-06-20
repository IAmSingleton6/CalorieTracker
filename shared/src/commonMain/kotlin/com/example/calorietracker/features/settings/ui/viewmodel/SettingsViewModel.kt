package com.example.calorietracker.features.settings.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calorietracker.features.settings.domain.repository.SettingsRepository
import com.example.calorietracker.features.settings.ui.state.SettingsState
import com.example.calorietracker.features.tracking.domain.repository.CalorieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    private val calorieRepository: CalorieRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    init {
        observeSettings()
    }

    private fun observeSettings() {
        viewModelScope.launch {
            settingsRepository.observeSettings().collect { prefs ->
                _state.value = SettingsState(
                    dailyGoal = prefs.dailyGoal,
                    goalInput = prefs.dailyGoal.toString()
                )
            }
        }
    }

    fun updateGoalInput(input: String) {
        _state.value = _state.value.copy(goalInput = input)
    }

    fun saveGoal() {
        val goal = _state.value.goalInput.toIntOrNull()
        if (goal != null && goal > 0) {
            viewModelScope.launch {
                settingsRepository.updateGoal(goal)
            }
        }
    }

    fun showResetConfirmation() {
        _state.value = _state.value.copy(showResetConfirmation = true)
    }

    fun dismissResetConfirmation() {
        _state.value = _state.value.copy(showResetConfirmation = false)
    }

    fun resetAllData() {
        viewModelScope.launch {
            calorieRepository.deleteAllEntries()
            _state.value = _state.value.copy(showResetConfirmation = false)
        }
    }
}
