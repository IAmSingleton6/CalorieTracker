package com.example.calorietracker.features.tracking.domain.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.example.calories.database.CalorieDatabase
import com.example.calorietracker.core.common.TimeProvider
import com.example.calorietracker.features.tracking.domain.model.CalorieEntry
import com.example.calorietracker.core.model.DailySummary
import com.example.calorietracker.features.tracking.domain.model.ProgressData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

internal class CalorieRepositoryImpl(
    database: CalorieDatabase,
    private val mapper: CalorieEntryMapper,
    private val timeProvider: TimeProvider
) : CalorieRepository {

    private val queries = database.calorieTrackerDbQueries

    override suspend fun addEntry(amount: Int, date: LocalDate) {
        queries.insertEntry(
            amount = amount.toLong(),
            entry_date = date.toString(),
            created_at = timeProvider.currentTimeMillis()
        )
    }

    override suspend fun deleteTodayEntries(date: LocalDate) {
        queries.deleteTodayEntries(date.toString())
    }

    override suspend fun deleteAllEntries() {
        queries.deleteAllEntries()
    }

    override fun observeTodayTotal(date: LocalDate): Flow<Int> {
        return queries.selectTodayTotal(date.toString())
            .asFlow()
            .mapToOne(Dispatchers.Default)
            .map { it.toInt() }
    }

    override suspend fun getTodayTotal(date: LocalDate): Int {
        return queries.selectTodayTotal(date.toString()).executeAsOne().toInt()
    }

    override fun observeEntries(date: LocalDate): Flow<List<CalorieEntry>> {
        return queries.selectTodayEntries(date.toString())
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { list -> list.map { mapper.toDomain(it) } }
    }

    override suspend fun getHistoryRange(startDate: LocalDate, endDate: LocalDate, goal: Int): List<DailySummary> {
        return queries.selectHistoryRange(
            startDate = startDate.toString(),
            endDate = endDate.toString()
        ).executeAsList().map { row ->
            DailySummary(
                date = LocalDate.parse(row.entry_date),
                progressData = ProgressData(
                    total = (row.SUM ?: 0L).toInt(),
                    goal = goal
                )
            )
        }
    }
}