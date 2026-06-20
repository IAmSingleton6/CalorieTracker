package com.example.calorietracker.features.tracking.domain.model

data class ProgressData(
    val total: Int,
    val goal: Int
) {
    val percentage: Float
        get() = if (goal > 0) total.toFloat() / goal else 0f

    val isOverGoal: Boolean
        get() = total > goal
}
