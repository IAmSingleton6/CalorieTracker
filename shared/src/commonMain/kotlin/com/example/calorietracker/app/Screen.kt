package com.example.calorietracker.app

internal sealed class Screen {
    data object Tracking : Screen()
    data object History : Screen()
    data object Settings : Screen()
}
