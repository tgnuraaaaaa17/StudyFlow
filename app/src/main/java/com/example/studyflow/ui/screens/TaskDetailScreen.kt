package com.example.studyflow.ui.screens

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.studyflow.R
import com.example.studyflow.data.Task
import com.example.studyflow.ui.theme.Spacing
import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview
import com.example.studyflow.data.sampleTasks
import com.example.studyflow.ui.theme.StudyFlowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    task: Task,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var completed by remember { mutableStateOf(task.isCompleted) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Task details") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Text("←") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "Illustration for ${task.title}",
                modifier = Modifier.fillMaxWidth().aspectRatio(16f / 9f)
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
                onClick = { completed = !completed },
                modifier = Modifier.fillMaxWidth().heightIn(min = Spacing.touchTarget)
            ) {
                Text(if (completed) "Completed ✓ (tap to undo)" else "Mark as completed")
            }
        }
    }

}
@Preview(showBackground = true)
@Composable
private fun TaskDetailScreenPreview() {
    StudyFlowTheme { TaskDetailScreen(task = sampleTasks[0], onBack = {}) }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TaskDetailScreenDarkPreview() {
    StudyFlowTheme { TaskDetailScreen(task = sampleTasks[0], onBack = {}) }
}