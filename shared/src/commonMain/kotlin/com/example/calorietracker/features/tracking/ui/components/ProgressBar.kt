package com.example.calorietracker.features.tracking.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.calorietracker.core.designsystem.formatOverGoal
import com.example.calorietracker.core.designsystem.formatProgress
import com.example.calorietracker.core.designsystem.Dimens

@Composable
internal fun CalorieProgressBar(
    current: Int,
    goal: Int,
    fraction: Float,
    barColor: Color,
    isOverGoal: Boolean,
    modifier: Modifier = Modifier
) {
    val animatedFraction by animateFloatAsState(
        targetValue = fraction.coerceIn(0f, 1.2f),
        animationSpec = tween(durationMillis = 500),
        label = "progress"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.spacingLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatProgress(current, goal),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = Dimens.spacingSmall)
        )

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.progressBarHeight)
        ) {
            val barWidth = size.width
            val barHeight = size.height
            val cornerRadius = CornerRadius(Dimens.progressBarCornerRadius.toPx())

            val cappedWidth = barWidth * animatedFraction.coerceIn(0f, 1f)
            val overWidth = if (isOverGoal) {
                barWidth * (animatedFraction - 1f).coerceIn(0f, 0.2f)
            } else 0f

            drawRoundRect(
                color = Color.LightGray.copy(alpha = 0.3f),
                cornerRadius = cornerRadius,
                size = Size(barWidth, barHeight)
            )

            drawRoundRect(
                color = barColor,
                cornerRadius = cornerRadius,
                size = Size(cappedWidth, barHeight)
            )

            if (isOverGoal && overWidth > 0f) {
                drawRoundRect(
                    color = barColor.copy(alpha = 0.6f),
                    cornerRadius = cornerRadius,
                    size = Size(overWidth, barHeight),
                    topLeft = Offset(cappedWidth, 0f)
                )
            }
        }

        if (isOverGoal) {
            Text(
                text = formatOverGoal(current - goal),
                style = MaterialTheme.typography.bodySmall,
                color = barColor,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = Dimens.spacingXSmall)
            )
        }
    }
}
