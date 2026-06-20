package com.example.calorietracker.core.common

import androidx.compose.runtime.Composable

@Composable
internal expect fun BackButtonHandler(
    enabled: Boolean = true,
    onBack: () -> Unit,
)
