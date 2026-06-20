package com.example.calorietracker.features.tracking.domain.mapper

import com.example.calorietracker.features.tracking.domain.model.ProgressData
import com.example.calorietracker.features.tracking.ui.state.TrackingState
import io.mockative.Mockable

@Mockable
internal interface TrackingStateMapper {
    fun map(data: ProgressData, dateText: String): TrackingState
}
