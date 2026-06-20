package com.example.calorietracker.features.tracking.domain.repository

import com.example.calories.database.Calorie_entry
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Instant

class CalorieEntryMapperImplTest {

    private val mapper = CalorieEntryMapperImpl()

    @Test
    fun `toDomain maps id correctly`() {
        // WHEN
        val domain = mapper.toDomain(dbEntry)

        // THEN
        assertEquals(1L, domain.id)
    }

    @Test
    fun `toDomain converts amount from Long to Int`() {
        // WHEN
        val domain = mapper.toDomain(dbEntry)

        // THEN
        assertEquals(500, domain.amount)
    }

    @Test
    fun `toDomain parses entry_date to LocalDate`() {
        // WHEN
        val domain = mapper.toDomain(dbEntry)

        // THEN
        assertEquals(LocalDate(2024, 1, 15), domain.date)
    }

    @Test
    fun `toDomain converts created_at to Instant`() {
        // WHEN
        val domain = mapper.toDomain(dbEntry)
        
        // THEN
        assertEquals(Instant.fromEpochMilliseconds(1705315200000L), domain.createdAt)
    }

    companion object {
        private val dbEntry = Calorie_entry(
            id = 1L,
            amount = 500L,
            entry_date = "2024-01-15",
            created_at = 1705315200000L
        )
    }
}
