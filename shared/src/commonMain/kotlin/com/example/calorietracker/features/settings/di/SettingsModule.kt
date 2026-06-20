package com.example.calorietracker.features.settings.di

import com.example.calorietracker.features.settings.domain.repository.SettingsRepositoryImpl
import com.example.calorietracker.features.settings.domain.repository.SettingsRepository
import com.example.calorietracker.features.settings.ui.viewmodel.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val settingsModule = module {
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }

    viewModel { SettingsViewModel(get(), get()) }
}
