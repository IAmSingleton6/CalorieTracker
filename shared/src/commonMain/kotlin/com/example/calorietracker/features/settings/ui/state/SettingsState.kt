package com.example.calorietracker.features.settings.ui.state

import com.example.calorietracker.core.common.Defaults

internal data class SettingsState(
    val dailyGoal: Int = Defaults.DAILY_GOAL,
    val goalInput: String = Defaults.DAILY_GOAL.toString(),
    val showResetConfirmation: Boolean = false
)
