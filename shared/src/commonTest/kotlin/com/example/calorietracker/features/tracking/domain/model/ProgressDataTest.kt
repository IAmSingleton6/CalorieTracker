package com.example.calorietracker.features.tracking.domain.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ProgressDataTest {

    @Test
    fun `percentage returns total divided by goal when goal is positive`() {
        // GIVEN
        val data = ProgressData(total = 500, goal = 2500)

        // WHEN
        val result = data.percentage

        // THEN
        assertEquals(0.2f, result)
    }

    @Test
    fun `percentage returns 1f when total equals goal`() {
        // GIVEN
        val data = ProgressData(total = 2500, goal = 2500)

        // WHEN
        val result = data.percentage

        // THEN
        assertEquals(1f, result)
    }

    @Test
    fun `percentage returns greater than 1f when total exceeds goal`() {
        // GIVEN
        val data = ProgressData(total = 3000, goal = 2500)

        // WHEN
        val result = data.percentage

        // THEN
        assertEquals(1.2f, result)
    }

    @Test
    fun `percentage returns zero when goal is zero`() {
        // GIVEN
        val data = ProgressData(total = 500, goal = 0)

        // WHEN
        val result = data.percentage

        // THEN
        assertEquals(0f, result)
    }

    @Test
    fun `percentage returns zero when total and goal are zero`() {
        // GIVEN
        val data = ProgressData(total = 0, goal = 0)

        // WHEN
        val result = data.percentage

        // THEN
        assertEquals(0f, result)
    }

    @Test
    fun `isOverGoal returns true when total exceeds goal`() {
        // GIVEN
        val data = ProgressData(total = 3000, goal = 2500)

        // WHEN
        val result = data.isOverGoal

        // THEN
        assertTrue(result)
    }

    @Test
    fun `isOverGoal returns false when total equals goal`() {
        // GIVEN
        val data = ProgressData(total = 2500, goal = 2500)

        // WHEN
        val result = data.isOverGoal

        // THEN
        assertFalse(result)
    }

    @Test
    fun `isOverGoal returns false when total is below goal`() {
        // GIVEN
        val data = ProgressData(total = 1500, goal = 2500)

        // WHEN
        val result = data.isOverGoal
        
        // THEN
        assertFalse(result)
    }
}
