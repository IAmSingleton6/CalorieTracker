package com.example.calorietracker.features.tracking.domain.repository

import com.example.calories.database.Calorie_entry
import com.example.calorietracker.features.tracking.domain.model.CalorieEntry

interface CalorieEntryMapper {
    fun toDomain(entry: Calorie_entry): CalorieEntry
}
