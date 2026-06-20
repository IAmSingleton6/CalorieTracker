package com.example.calorietracker.features.history.ui.state

import com.example.calorietracker.core.model.DailySummary
import com.example.calorietracker.features.history.domain.model.HistoryPeriod

internal data class HistoryState(
    val period: HistoryPeriod = HistoryPeriod.Week,
    val summaries: List<DailySummary> = emptyList(),
    val average: Int = 0,
    val adherencePercent: Float = 0f,
    val isLoading: Boolean = true
)
