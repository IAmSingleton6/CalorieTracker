package com.example.calorietracker

import androidx.compose.ui.window.ComposeUIViewController
import com.example.calorietracker.app.appModule
import org.koin.compose.KoinApplication

fun MainViewController() = ComposeUIViewController {
    KoinApplication(application = {
        modules(appModule)
    }) {
        App()
    }
}
