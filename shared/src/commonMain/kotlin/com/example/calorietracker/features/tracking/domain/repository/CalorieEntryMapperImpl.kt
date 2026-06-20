package com.example.calorietracker.features.tracking.domain.repository

import com.example.calories.database.Calorie_entry
import com.example.calorietracker.features.tracking.domain.model.CalorieEntry
import kotlin.time.Instant
import kotlinx.datetime.LocalDate

internal class CalorieEntryMapperImpl : CalorieEntryMapper {

    override fun toDomain(entry: Calorie_entry): CalorieEntry {
        return CalorieEntry(
            id = entry.id,
            amount = entry.amount.toInt(),
            date = LocalDate.parse(entry.entry_date),
            createdAt = Instant.fromEpochMilliseconds(entry.created_at)
        )
    }
}
