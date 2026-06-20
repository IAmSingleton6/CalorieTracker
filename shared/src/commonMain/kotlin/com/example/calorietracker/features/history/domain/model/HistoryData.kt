package com.example.calorietracker.features.history.domain.model

import com.example.calorietracker.core.model.DailySummary

data class HistoryData(
    val summaries: List<DailySummary>,
    val period: HistoryPeriod,
    val average: Int,
    val adherencePercent: Float
)
