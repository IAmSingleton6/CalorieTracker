package com.example.calorietracker.core.database

import org.koin.core.module.Module
import org.koin.dsl.module

actual val databasePlatformModule: Module = module {
    single { DatabaseDriverFactory() }
}
