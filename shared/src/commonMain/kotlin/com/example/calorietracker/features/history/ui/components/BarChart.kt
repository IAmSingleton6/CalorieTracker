package com.example.calorietracker.features.history.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import calorietracker.shared.generated.resources.Res
import calorietracker.shared.generated.resources.daily_calories_chart
import calorietracker.shared.generated.resources.goal_label_chart
import calorietracker.shared.generated.resources.no_data
import com.example.calorietracker.core.common.Defaults
import com.example.calorietracker.core.designsystem.Dimens
import com.example.calorietracker.core.model.DailySummary
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun BarChart(
    summaries: List<DailySummary>,
    modifier: Modifier = Modifier
) {
    if (summaries.isEmpty()) {
        Text(
            text = stringResource(Res.string.no_data),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = modifier.padding(Dimens.spacingMedium)
        )
        return
    }

    val goal = summaries.firstOrNull()?.progressData?.goal ?: Defaults.DAILY_GOAL
    val maxValue = maxOf(summaries.maxOf { it.progressData.total }, goal)
    val goalLabel = stringResource(Res.string.goal_label_chart)

    val primary = MaterialTheme.colorScheme.primary
    val secondary = MaterialTheme.colorScheme.secondary
    val tertiary = MaterialTheme.colorScheme.tertiary
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(Res.string.daily_calories_chart),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = Dimens.spacingMedium)
        )

        Spacer(modifier = Modifier.height(Dimens.spacingMedium))

        val textMeasurer = rememberTextMeasurer()

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = Dimens.spacingMedium)
        ) {
            if (summaries.isEmpty()) return@Canvas

            val barCount = summaries.size
            val totalWidth = size.width
            val barGap = 4.dp.toPx()
            val barWidth = (totalWidth - barGap * (barCount + 1)) / barCount
            val chartHeight = size.height - 24.dp.toPx()

            summaries.forEachIndexed { index, summary ->
                val barHeight = if (maxValue > 0) {
                    (summary.progressData.total.toFloat() / maxValue) * chartHeight
                } else 0f

                val x = barGap + index * (barWidth + barGap)
                val y = size.height - 24.dp.toPx() - barHeight

                val color = when {
                    summary.progressData.isOverGoal -> tertiary
                    summary.progressData.percentage >= 0.8f -> secondary
                    else -> primary
                }

                drawRoundRect(
                    color = color,
                    cornerRadius = CornerRadius(4.dp.toPx()),
                    topLeft = Offset(x, y),
                    size = Size(barWidth, barHeight)
                )
            }

            val goalY = size.height - 24.dp.toPx() - (goal.toFloat() / maxValue) * chartHeight
            drawLine(
                color = onSurfaceVariant.copy(alpha = 0.5f),
                start = Offset(0f, goalY),
                end = Offset(size.width, goalY),
                strokeWidth = 1.dp.toPx()
            )

            val goalTextObj = textMeasurer.measure(
                text = goalLabel,
                style = TextStyle(
                    color = onSurfaceVariant,
                    fontSize = 10.sp
                )
            )
            drawText(
                textLayoutResult = goalTextObj,
                topLeft = Offset(size.width - goalTextObj.size.width - 4.dp.toPx(), goalY - goalTextObj.size.height - 2.dp.toPx())
            )
        }
    }
}
