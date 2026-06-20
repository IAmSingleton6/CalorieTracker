package com.example.calorietracker.app

import org.jetbrains.compose.resources.DrawableResource

internal data class BottomNavItem(
    val screen: Screen,
    val icon: DrawableResource,
    val label: String,
)
