package com.example.calorietracker.core.designsystem

import kotlin.test.Test
import kotlin.test.assertEquals

class KcalFormatTest {

    @Test
    fun `formatWithCommas formats zero`() {
        // GIVEN
        val input = 0

        // WHEN
        val result = input.formatWithCommas()

        // THEN
        assertEquals("0", result)
    }

    @Test
    fun `formatWithCommas formats hundreds`() {
        // GIVEN
        val input = 100

        // WHEN
        val result = input.formatWithCommas()

        // THEN
        assertEquals("100", result)
    }

    @Test
    fun `formatWithCommas formats thousands`() {
        // GIVEN
        val input = 1000

        // WHEN
        val result = input.formatWithCommas()

        // THEN
        assertEquals("1,000", result)
    }

    @Test
    fun `formatWithCommas formats ten thousands`() {
        // GIVEN
        val input = 12500

        // WHEN
        val result = input.formatWithCommas()

        // THEN
        assertEquals("12,500", result)
    }

    @Test
    fun `formatWithCommas formats hundred thousands`() {
        // GIVEN
        val input = 123456

        // WHEN
        val result = input.formatWithCommas()

        // THEN
        assertEquals("123,456", result)
    }

    @Test
    fun `formatWithCommas formats millions`() {
        // GIVEN
        val input = 1234567

        // WHEN
        val result = input.formatWithCommas()

        // THEN
        assertEquals("1,234,567", result)
    }

    @Test
    fun `toKcalString formats with kcal suffix`() {
        // GIVEN
        val input = 2500

        // WHEN
        val result = input.toKcalString()

        // THEN
        assertEquals("2,500 kcal", result)
    }

    @Test
    fun `formatProgress formats current over goal`() {
        // GIVEN
        val current = 500
        val goal = 2500

        // WHEN
        val result = formatProgress(current, goal)

        // THEN
        assertEquals("500 / 2,500 kcal", result)
    }

    @Test
    fun `formatOverGoal formats excess message`() {
        // GIVEN
        val excess = 300

        // WHEN
        val result = formatOverGoal(excess)

        // THEN
        assertEquals("Over goal by 300 kcal", result)
    }
}
