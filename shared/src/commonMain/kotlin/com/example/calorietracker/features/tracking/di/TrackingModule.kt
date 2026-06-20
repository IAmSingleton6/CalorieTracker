package com.example.calorietracker.features.tracking.di

import com.example.calorietracker.features.tracking.domain.mapper.TrackingStateMapper
import com.example.calorietracker.features.tracking.domain.mapper.TrackingStateMapperImpl
import com.example.calorietracker.features.tracking.domain.repository.CalorieEntryMapper
import com.example.calorietracker.features.tracking.domain.repository.CalorieEntryMapperImpl
import com.example.calorietracker.features.tracking.domain.repository.CalorieRepositoryImpl
import com.example.calorietracker.features.tracking.domain.repository.CalorieRepository
import com.example.calorietracker.features.tracking.domain.usecase.AddCaloriesUseCase
import com.example.calorietracker.features.tracking.domain.usecase.AddCaloriesUseCaseImpl
import com.example.calorietracker.features.tracking.domain.usecase.GetFormattedDateUseCase
import com.example.calorietracker.features.tracking.domain.usecase.GetFormattedDateUseCaseImpl
import com.example.calorietracker.features.tracking.domain.usecase.ObserveProgressUseCase
import com.example.calorietracker.features.tracking.domain.usecase.ObserveProgressUseCaseImpl
import com.example.calorietracker.features.tracking.domain.usecase.ObserveTodayCaloriesUseCase
import com.example.calorietracker.features.tracking.domain.usecase.ObserveTodayCaloriesUseCaseImpl
import com.example.calorietracker.features.tracking.domain.usecase.ResetTodayCaloriesUseCase
import com.example.calorietracker.features.tracking.domain.usecase.ResetTodayCaloriesUseCaseImpl
import com.example.calorietracker.features.tracking.ui.viewmodel.TrackingViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val trackingModule = module {
    single<TrackingStateMapper> { TrackingStateMapperImpl() }
    single<CalorieEntryMapper> { CalorieEntryMapperImpl() }
    single<CalorieRepository> { CalorieRepositoryImpl(get(), get(), get()) }

    factory<AddCaloriesUseCase> { AddCaloriesUseCaseImpl(get(), get()) }
    factory<ObserveTodayCaloriesUseCase> { ObserveTodayCaloriesUseCaseImpl(get(), get()) }
    factory<ObserveProgressUseCase> { ObserveProgressUseCaseImpl(get(), get(), get()) }
    factory<ResetTodayCaloriesUseCase> { ResetTodayCaloriesUseCaseImpl(get(), get()) }
    factory<GetFormattedDateUseCase> { GetFormattedDateUseCaseImpl(get()) }

    viewModel { TrackingViewModel(get(), get(), get(), get(), get()) }
}
