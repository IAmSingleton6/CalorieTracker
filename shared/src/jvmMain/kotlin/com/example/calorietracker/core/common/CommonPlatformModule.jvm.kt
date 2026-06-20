package com.example.calorietracker.core.common

import org.koin.core.module.Module
import org.koin.dsl.module

actual val commonPlatformModule: Module = module {
    single<TimeProvider> { TimeProviderImpl() }
}
