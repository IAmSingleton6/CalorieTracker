package com.example.calorietracker.features.history.ui.screen

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import calorietracker.shared.generated.resources.Res
import calorietracker.shared.generated.resources.back
import calorietracker.shared.generated.resources.history_title
import calorietracker.shared.generated.resources.period_month
import calorietracker.shared.generated.resources.period_week
import calorietracker.shared.generated.resources.stat_average
import calorietracker.shared.generated.resources.stat_goal_adherence
import com.example.calorietracker.core.common.BackButtonHandler
import com.example.calorietracker.core.common.koinScopedViewModel
import com.example.calorietracker.core.designsystem.Dimens
import com.example.calorietracker.core.designsystem.formatWithCommas
import com.example.calorietracker.core.designsystem.toKcalString
import com.example.calorietracker.features.history.domain.model.HistoryPeriod
import com.example.calorietracker.features.history.ui.components.BarChart
import com.example.calorietracker.features.history.ui.viewmodel.HistoryViewModel
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HistoryScreen(
    onBack: () -> Unit,
    viewModel: HistoryViewModel = koinScopedViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BackButtonHandler(onBack = onBack)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.history_title)) },
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
                .padding(horizontal = Dimens.spacingMedium),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(Dimens.spacingMedium))

            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimens.spacingSmall)
            ) {
                FilterChip(
                    selected = state.period == HistoryPeriod.Week,
                    onClick = { viewModel.setPeriod(HistoryPeriod.Week) },
                    label = { Text(stringResource(Res.string.period_week)) }
                )
                FilterChip(
                    selected = state.period == HistoryPeriod.Month,
                    onClick = { viewModel.setPeriod(HistoryPeriod.Month) },
                    label = { Text(stringResource(Res.string.period_month)) }
                )
            }

            Spacer(modifier = Modifier.height(Dimens.spacingLarge))

            BarChart(summaries = state.summaries)

            Spacer(modifier = Modifier.height(Dimens.spacingLarge))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.spacingMedium)
            ) {
                StatCard(
                    title = stringResource(Res.string.stat_average),
                    value = state.average.toKcalString(),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = stringResource(Res.string.stat_goal_adherence),
                    value = "${state.adherencePercent.toInt().formatWithCommas()}%",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(Dimens.spacingMedium))
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.spacingMedium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(Dimens.spacingSmall))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
