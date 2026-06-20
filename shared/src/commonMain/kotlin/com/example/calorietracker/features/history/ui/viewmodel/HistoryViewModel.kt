package com.example.calorietracker.features.history.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calorietracker.features.history.domain.model.HistoryPeriod
import com.example.calorietracker.features.history.domain.usecase.GetHistoryUseCase
import com.example.calorietracker.features.history.domain.usecase.GetStatsUseCase
import com.example.calorietracker.features.history.ui.state.HistoryState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class HistoryViewModel(
    private val getHistoryUseCase: GetHistoryUseCase,
    private val getStatsUseCase: GetStatsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryState())
    val state: StateFlow<HistoryState> = _state.asStateFlow()

    init {
        loadHistory()
    }

    fun loadHistory(days: Int = HistoryPeriod.Week.days) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val summaries = getHistoryUseCase(days)
            val (average, adherence) = getStatsUseCase(days)
            _state.value = _state.value.copy(
                summaries = summaries,
                average = average,
                adherencePercent = adherence,
                isLoading = false
            )
        }
    }

    fun setPeriod(period: HistoryPeriod) {
        _state.value = _state.value.copy(period = period)
        loadHistory(period.days)
    }
}
