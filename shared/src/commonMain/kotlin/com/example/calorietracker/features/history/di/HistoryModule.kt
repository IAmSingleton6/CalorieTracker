package com.example.calorietracker.features.history.di

import com.example.calorietracker.features.history.domain.usecase.GetHistoryUseCase
import com.example.calorietracker.features.history.domain.usecase.GetHistoryUseCaseImpl
import com.example.calorietracker.features.history.domain.usecase.GetStatsUseCase
import com.example.calorietracker.features.history.domain.usecase.GetStatsUseCaseImpl
import com.example.calorietracker.features.history.ui.viewmodel.HistoryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val historyModule = module {
    factory<GetHistoryUseCase> { GetHistoryUseCaseImpl(get(), get(), get()) }
    factory<GetStatsUseCase> { GetStatsUseCaseImpl(get(), get(), get()) }

    viewModel { HistoryViewModel(get(), get()) }
}
