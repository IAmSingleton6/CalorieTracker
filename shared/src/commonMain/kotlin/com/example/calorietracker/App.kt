package com.example.calorietracker

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import calorietracker.shared.generated.resources.Res
import calorietracker.shared.generated.resources.ic_history
import calorietracker.shared.generated.resources.ic_settings
import calorietracker.shared.generated.resources.ic_today
import calorietracker.shared.generated.resources.nav_history
import calorietracker.shared.generated.resources.nav_settings
import calorietracker.shared.generated.resources.nav_today
import com.example.calorietracker.app.BottomNavItem
import com.example.calorietracker.app.Screen
import com.example.calorietracker.core.designsystem.CalorieTrackerTheme
import com.example.calorietracker.features.history.ui.screen.HistoryScreen
import com.example.calorietracker.features.settings.ui.screen.SettingsScreen
import com.example.calorietracker.features.tracking.ui.screen.TrackingScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(darkTheme: Boolean = isSystemInDarkTheme()) {
    CalorieTrackerTheme(darkTheme = darkTheme) {
        var currentScreen by remember { mutableStateOf<Screen>(Screen.Tracking) }
        val onBack = { currentScreen = Screen.Tracking }
        val navToday = stringResource(Res.string.nav_today)
        val navHistory = stringResource(Res.string.nav_history)
        val navSettings = stringResource(Res.string.nav_settings)

        val items = remember(navToday, navHistory, navSettings) {
            listOf(
                BottomNavItem(Screen.Tracking, Res.drawable.ic_today, navToday),
                BottomNavItem(Screen.History, Res.drawable.ic_history, navHistory),
                BottomNavItem(Screen.Settings, Res.drawable.ic_settings, navSettings),
            )
        }

        val navColors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Scaffold(
            bottomBar = {
                NavigationBar {
                    items.forEach { item ->
                        NavigationBarItem(
                            selected = currentScreen == item.screen,
                            onClick = { currentScreen = item.screen },
                            icon = {
                                Icon(
                                    painter = painterResource(item.icon),
                                    contentDescription = item.label,
                                )
                            },
                            label = { Text(item.label) },
                            colors = navColors,
                        )
                    }
                }
            }
        ) { padding ->
            AnimatedContent(
                targetState = currentScreen,
                modifier = Modifier.padding(padding),
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "screen_transition"
            ) { screen ->
                when (screen) {
                    is Screen.Tracking -> TrackingScreen()

                    is Screen.History -> HistoryScreen(onBack = onBack)

                    is Screen.Settings -> SettingsScreen(onBack = onBack)
                }
            }
        }
    }
}
