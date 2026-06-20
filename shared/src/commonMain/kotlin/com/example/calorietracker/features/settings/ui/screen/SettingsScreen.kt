package com.example.calorietracker.features.settings.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import calorietracker.shared.generated.resources.Res
import calorietracker.shared.generated.resources.back
import calorietracker.shared.generated.resources.cancel
import calorietracker.shared.generated.resources.reset_all_data
import calorietracker.shared.generated.resources.reset_confirm
import calorietracker.shared.generated.resources.reset_dialog_text
import calorietracker.shared.generated.resources.reset_dialog_title
import calorietracker.shared.generated.resources.settings_title
import com.example.calorietracker.core.common.BackButtonHandler
import com.example.calorietracker.core.common.koinScopedViewModel
import com.example.calorietracker.core.designsystem.Dimens
import com.example.calorietracker.features.settings.ui.components.GoalEditor
import com.example.calorietracker.features.settings.ui.viewmodel.SettingsViewModel
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsScreen(
    onBack: () -> Unit,
    viewModel: SettingsViewModel = koinScopedViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BackButtonHandler(onBack = onBack)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text(
                            stringResource(Res.string.back),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(horizontal = Dimens.spacingMedium)
        ) {
            Spacer(modifier = Modifier.height(Dimens.spacingXLarge))

            GoalEditor(
                goalInput = state.goalInput,
                onGoalInputChange = viewModel::updateGoalInput,
                onSaveGoal = viewModel::saveGoal
            )

            Spacer(modifier = Modifier.height(Dimens.spacingXXLarge))

            OutlinedButton(
                onClick = viewModel::showResetConfirmation,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(stringResource(Res.string.reset_all_data))
            }
        }
    }

    if (state.showResetConfirmation) {
        AlertDialog(
            onDismissRequest = viewModel::dismissResetConfirmation,
            title = { Text(stringResource(Res.string.reset_dialog_title)) },
            text = { Text(stringResource(Res.string.reset_dialog_text)) },
            confirmButton = {
                TextButton(
                    onClick = viewModel::resetAllData,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(stringResource(Res.string.reset_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = viewModel::dismissResetConfirmation) {
                    Text(stringResource(Res.string.cancel))
                }
            }
        )
    }
}
