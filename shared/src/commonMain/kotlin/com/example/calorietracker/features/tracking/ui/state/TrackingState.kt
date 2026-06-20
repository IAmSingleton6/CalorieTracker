package com.example.calorietracker.features.tracking.ui.state

import com.example.calorietracker.core.common.Defaults

internal data class TrackingState(
    val calories: Int = 0,
    val goal: Int = Defaults.DAILY_GOAL,
    val dateText: String = "",
    val progressFraction: Float = 0f,
    val isOverGoal: Boolean = false
)
