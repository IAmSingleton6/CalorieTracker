package com.example.calorietracker.features.tracking.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calorietracker.features.tracking.domain.mapper.TrackingStateMapper
import com.example.calorietracker.features.tracking.domain.usecase.AddCaloriesUseCase
import com.example.calorietracker.features.tracking.domain.usecase.GetFormattedDateUseCase
import com.example.calorietracker.features.tracking.domain.usecase.ObserveProgressUseCase
import com.example.calorietracker.features.tracking.domain.usecase.ResetTodayCaloriesUseCase
import com.example.calorietracker.features.tracking.ui.state.TrackingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class TrackingViewModel(
    private val trackingStateMapper: TrackingStateMapper,
    private val addCaloriesUseCase: AddCaloriesUseCase,
    private val observeProgressUseCase: ObserveProgressUseCase,
    private val resetTodayCaloriesUseCase: ResetTodayCaloriesUseCase,
    private val getFormattedDateUseCase: GetFormattedDateUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(TrackingState())
    val state: StateFlow<TrackingState> = _state.asStateFlow()

    init {
        observeProgress()
    }

    private fun observeProgress() {
        viewModelScope.launch {
            observeProgressUseCase().collect { progress ->
                _state.value = trackingStateMapper.map(
                    data = progress,
                    dateText = getFormattedDateUseCase()
                )
            }
        }
    }

    fun addCalories(amount: Int) {
        viewModelScope.launch {
            addCaloriesUseCase(amount)
        }
    }

    fun resetCalories() {
        viewModelScope.launch {
            resetTodayCaloriesUseCase()
        }
    }
}
