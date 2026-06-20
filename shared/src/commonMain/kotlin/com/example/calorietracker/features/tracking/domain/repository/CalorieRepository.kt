package com.example.calorietracker.features.tracking.domain.repository

import com.example.calorietracker.core.model.DailySummary
import com.example.calorietracker.features.tracking.domain.model.CalorieEntry
import io.mockative.Mockable
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

@Mockable
interface CalorieRepository {
    suspend fun addEntry(amount: Int, date: LocalDate)
    suspend fun deleteTodayEntries(date: LocalDate)
    suspend fun deleteAllEntries()
    fun observeTodayTotal(date: LocalDate): Flow<Int>
    suspend fun getTodayTotal(date: LocalDate): Int
    fun observeEntries(date: LocalDate): Flow<List<CalorieEntry>>
    suspend fun getHistoryRange(startDate: LocalDate, endDate: LocalDate, goal: Int): List<DailySummary>
}
