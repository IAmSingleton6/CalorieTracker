package com.example.calorietracker.app

import com.example.calorietracker.core.common.commonPlatformModule
import com.example.calorietracker.core.database.databaseModule
import com.example.calorietracker.core.database.databasePlatformModule
import com.example.calorietracker.features.history.di.historyModule
import com.example.calorietracker.features.settings.di.settingsModule
import com.example.calorietracker.features.tracking.di.trackingModule
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module = module {
    includes(commonPlatformModule, databasePlatformModule, databaseModule, trackingModule, historyModule, settingsModule)
}
