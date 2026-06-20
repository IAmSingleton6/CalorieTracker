package com.example.calorietracker.core.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object CalorieColors {
    val Green = Color(0xFF4CAF50)
    val GreenContainer = Color(0xFFC8E6C9)
    val Amber = Color(0xFFFF9800)
    val AmberContainer = Color(0xFFFFE0B2)
    val Red = Color(0xFFF44336)
    val RedContainer = Color(0xFFFFCDD2)

    val Background = Color(0xFFF5F5F5)
    val Surface = Color(0xFFFFFFFF)
    val OnSurface = Color(0xFF1C1B1F)
    val OnSurfaceVariant = Color(0xFF49454F)
    val Outline = Color(0xFF79747E)
}

object CalorieDarkColors {
    val Green = Color(0xFF66BB6A)
    val GreenContainer = Color(0xFF1B5E20)
    val Amber = Color(0xFFFFB74D)
    val AmberContainer = Color(0xFFE65100)
    val Red = Color(0xFFEF5350)
    val RedContainer = Color(0xFFC62828)

    val Background = Color(0xFF1C1B1F)
    val Surface = Color(0xFF2B2930)
    val OnSurface = Color(0xFFE6E1E5)
    val OnSurfaceVariant = Color(0xFFCAC4D0)
    val Outline = Color(0xFF938F99)
}

@Composable
fun progressColor(fraction: Float): Color {
    val scheme = MaterialTheme.colorScheme
    return when {
        fraction < 0.8f -> scheme.primary
        fraction <= 1.0f -> scheme.secondary
        else -> scheme.tertiary
    }
}
