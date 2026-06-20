package com.example.calorietracker.features.tracking.domain.mapper

import com.example.calorietracker.features.tracking.domain.model.ProgressData
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TrackingStateMapperImplTest {

    private val mapper = TrackingStateMapperImpl()

    @Test
    fun `map sets calories from progress total`() {
        // GIVEN
        val data = ProgressData(total = 500, goal = 2500)

        // WHEN
        val state = mapper.map(data, "Monday, January 15")

        // THEN
        assertEquals(500, state.calories)
    }

    @Test
    fun `map sets goal from progress goal`() {
        // GIVEN
        val data = ProgressData(total = 500, goal = 2000)

        // WHEN
        val state = mapper.map(data, "Monday, January 15")

        // THEN
        assertEquals(2000, state.goal)
    }

    @Test
    fun `map sets dateText from argument`() {
        // GIVEN
        val data = ProgressData(total = 500, goal = 2500)

        // WHEN
        val state = mapper.map(data, "Monday, January 15")

        // THEN
        assertEquals("Monday, January 15", state.dateText)
    }

    @Test
    fun `map computes progressFraction from percentage within range`() {
        // GIVEN
        val data = ProgressData(total = 500, goal = 2500)

        // WHEN
        val state = mapper.map(data, "")

        // THEN
        assertEquals(0.2f, state.progressFraction)
    }

    @Test
    fun `map coerces progressFraction to minimum of zero`() {
        // GIVEN
        val data = ProgressData(total = -100, goal = 2500)

        // WHEN
        val state = mapper.map(data, "")

        // THEN
        assertEquals(0f, state.progressFraction)
    }

    @Test
    fun `map coerces progressFraction to maximum of 1 point 2`() {
        // GIVEN
        val data = ProgressData(total = 5000, goal = 2500)

        // WHEN
        val state = mapper.map(data, "")

        // THEN
        assertEquals(1.2f, state.progressFraction)
    }

    @Test
    fun `map sets isOverGoal true when total exceeds goal`() {
        // GIVEN
        val data = ProgressData(total = 3000, goal = 2500)

        // WHEN
        val state = mapper.map(data, "")

        // THEN
        assertTrue(state.isOverGoal)
    }

    @Test
    fun `map sets isOverGoal false when total is under goal`() {
        // GIVEN
        val data = ProgressData(total = 1500, goal = 2500)

        // WHEN
        val state = mapper.map(data, "")

        // THEN
        assertFalse(state.isOverGoal)
    }
}
