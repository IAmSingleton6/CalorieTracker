package com.example.calorietracker

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.calorietracker.app.appModule
import org.koin.compose.KoinApplication

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "CalorieTracker",
    ) {
        KoinApplication(application = {
            modules(appModule)
        }) {
            App()
        }
    }
}
