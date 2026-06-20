package com.example.calorietracker.features.settings.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import calorietracker.shared.generated.resources.Res
import calorietracker.shared.generated.resources.daily_calorie_goal
import calorietracker.shared.generated.resources.goal_label
import com.example.calorietracker.core.common.Defaults
import com.example.calorietracker.core.designsystem.Dimens
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun GoalEditor(
    goalInput: String,
    onGoalInputChange: (String) -> Unit,
    onSaveGoal: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(Res.string.daily_calorie_goal),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(Dimens.spacingMedium))

        OutlinedTextField(
            value = goalInput,
            onValueChange = onGoalInputChange,
            label = { Text(stringResource(Res.string.goal_label)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(Dimens.spacingSmall))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(Dimens.spacingSmall)
        ) {
            Defaults.PRESET_GOALS.forEach { preset ->
                AssistChip(
                    onClick = {
                        onGoalInputChange(preset.toString())
                        onSaveGoal()
                    },
                    label = { Text("$preset") },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }
    }
}
