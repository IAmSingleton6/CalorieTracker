package com.example.calorietracker.features.tracking.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import calorietracker.shared.generated.resources.Res
import calorietracker.shared.generated.resources.reset_day
import com.example.calorietracker.core.common.koinScopedViewModel
import com.example.calorietracker.core.designsystem.Dimens
import com.example.calorietracker.core.designsystem.progressColor
import com.example.calorietracker.features.tracking.ui.components.CalorieProgressBar
import com.example.calorietracker.features.tracking.ui.components.CalorieTotalDisplay
import com.example.calorietracker.features.tracking.ui.components.QuickAddButton
import com.example.calorietracker.features.tracking.ui.viewmodel.TrackingViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun TrackingScreen(
    viewModel: TrackingViewModel = koinScopedViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val barColor = progressColor(state.progressFraction)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Dimens.spacingMedium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(Dimens.spacingXXLarge))

        CalorieTotalDisplay(
            calories = state.calories,
            dateText = state.dateText
        )

        Spacer(modifier = Modifier.height(Dimens.spacingLarge))

        CalorieProgressBar(
            current = state.calories,
            goal = state.goal,
            fraction = state.progressFraction,
            barColor = barColor,
            isOverGoal = state.isOverGoal
        )

        Spacer(modifier = Modifier.height(Dimens.spacingXXLarge))

        QuickAddGrid(
            onAddCalories = viewModel::addCalories
        )

        Spacer(modifier = Modifier.height(Dimens.spacingMedium))

        OutlinedButton(
            onClick = viewModel::resetCalories,
            modifier = Modifier.height(36.dp)
        ) {
            Text(
                text = stringResource(Res.string.reset_day),
                style = MaterialTheme.typography.labelSmall
            )
        }

        Spacer(modifier = Modifier.height(Dimens.spacingXXLarge))
    }
}

@Composable
private fun QuickAddGrid(
    onAddCalories: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimens.spacingMedium)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            QuickAddButton(amount = 50, onClick = onAddCalories)
            QuickAddButton(amount = 100, onClick = onAddCalories)
            QuickAddButton(amount = 200, onClick = onAddCalories)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            QuickAddButton(amount = 500, onClick = onAddCalories)
            QuickAddButton(amount = 1000, onClick = onAddCalories)
        }
    }
}
