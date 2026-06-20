package com.example.calorietracker.core.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = CalorieColors.Green,
    onPrimary = CalorieColors.Surface,
    primaryContainer = CalorieColors.GreenContainer,
    secondary = CalorieColors.Amber,
    onSecondary = CalorieColors.Surface,
    secondaryContainer = CalorieColors.AmberContainer,
    tertiary = CalorieColors.Red,
    onTertiary = CalorieColors.Surface,
    tertiaryContainer = CalorieColors.RedContainer,
    background = CalorieColors.Background,
    surface = CalorieColors.Surface,
    onBackground = CalorieColors.OnSurface,
    onSurface = CalorieColors.OnSurface,
    onSurfaceVariant = CalorieColors.OnSurfaceVariant,
    outline = CalorieColors.Outline
)

private val DarkColorScheme = darkColorScheme(
    primary = CalorieDarkColors.Green,
    onPrimary = CalorieDarkColors.Background,
    primaryContainer = CalorieDarkColors.GreenContainer,
    secondary = CalorieDarkColors.Amber,
    onSecondary = CalorieDarkColors.Background,
    secondaryContainer = CalorieDarkColors.AmberContainer,
    tertiary = CalorieDarkColors.Red,
    onTertiary = CalorieDarkColors.Background,
    tertiaryContainer = CalorieDarkColors.RedContainer,
    background = CalorieDarkColors.Background,
    surface = CalorieDarkColors.Surface,
    onBackground = CalorieDarkColors.OnSurface,
    onSurface = CalorieDarkColors.OnSurface,
    onSurfaceVariant = CalorieDarkColors.OnSurfaceVariant,
    outline = CalorieDarkColors.Outline
)

@Composable
fun CalorieTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CalorieTypography,
        content = content
    )
}
