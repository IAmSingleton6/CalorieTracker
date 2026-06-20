package com.example.calorietracker.core.database

import com.example.calories.database.CalorieDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { get<DatabaseDriverFactory>().createDriver() }
    single { CalorieDatabase(get()) }
}
