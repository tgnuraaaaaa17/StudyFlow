package com.example.deadliner.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.deadliner.R
import com.example.deadliner.data.Task
import com.example.deadliner.data.sampleTasks
import com.example.deadliner.ui.TasksViewModel
import com.example.deadliner.ui.theme.DeadlinerTheme
import com.example.deadliner.ui.theme.Spacing

@Composable
fun TaskDetailRoute(
    taskId: Int?,
    viewModel: TasksViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val task = uiState.allTasks.firstOrNull { it.id == taskId }
    if (task != null) {
        TaskDetailScreen(
            task = task,
            onToggleCompleted = { viewModel.onToggleCompleted(task.id) },
            onBack = onBack
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    task: Task,
    onToggleCompleted: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Task details") },
                navigationIcon = { IconButton(onClick = onBack) { Text("←") } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            Image(
                painter = painterResource(R.drawable.deadliner_banner),
                contentDescription = "Illustration for ${task.title}",
                modifier = Modifier.fillMaxWidth().aspectRatio(16f / 9f),
                contentScale = ContentScale.Crop
            )
            Text(
                text = task.title,
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                Text(task.subject, style = MaterialTheme.typography.labelLarge)
                Text("·")
                Text("Deadline: ${task.deadline}", style = MaterialTheme.typography.labelLarge)
            }
            Text(
                text = task.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis
            )
            Button(
                onClick = onToggleCompleted,
                modifier = Modifier.fillMaxWidth().heightIn(min = Spacing.touchTarget)
            ) {
                Text(if (task.isCompleted) "Completed ✓ (tap to undo)" else "Mark as completed")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskDetailScreenPreview() {
    DeadlinerTheme { TaskDetailScreen(task = sampleTasks[0], onToggleCompleted = {}, onBack = {}) }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TaskDetailScreenDarkPreview() {
    DeadlinerTheme {
        TaskDetailScreen(task = sampleTasks[0].copy(isCompleted = true), onToggleCompleted = {}, onBack = {})
    }
}