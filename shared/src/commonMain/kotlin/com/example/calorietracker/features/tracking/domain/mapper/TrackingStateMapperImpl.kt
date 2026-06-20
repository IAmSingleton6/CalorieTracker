package com.example.calorietracker.features.tracking.domain.mapper

import com.example.calorietracker.features.tracking.domain.model.ProgressData
import com.example.calorietracker.features.tracking.ui.state.TrackingState

internal class TrackingStateMapperImpl : TrackingStateMapper {

    override fun map(data: ProgressData, dateText: String): TrackingState {
        return TrackingState(
            calories = data.total,
            goal = data.goal,
            dateText = dateText,
            progressFraction = data.percentage.coerceIn(0f, 1.2f),
            isOverGoal = data.isOverGoal
        )
    }
}
