package com.example.calorietracker.features.tracking.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.calorietracker.core.designsystem.toKcalString
import com.example.calorietracker.core.designsystem.Dimens

@Composable
internal fun CalorieTotalDisplay(
    calories: Int,
    dateText: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.spacingLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedContent(
            targetState = calories,
            transitionSpec = {
                slideInVertically(
                    animationSpec = tween(300),
                    initialOffsetY = { it / 4 }
                ) togetherWith
                        slideOutVertically(
                            animationSpec = tween(300),
                            targetOffsetY = { -it / 4 }
                        )
            },
            label = "calorie_total"
        ) { targetCalories ->
            Text(
                text = targetCalories.toKcalString(),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = dateText,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = Dimens.spacingSmall)
        )
    }
}
