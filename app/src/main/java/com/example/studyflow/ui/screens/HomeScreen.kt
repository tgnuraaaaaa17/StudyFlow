package com.example.studyflow.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.studyflow.data.Task
import com.example.studyflow.data.sampleSubjects
import com.example.studyflow.data.sampleTasks
import com.example.studyflow.ui.components.SectionHeader
import com.example.studyflow.ui.components.SubjectChip
import com.example.studyflow.ui.components.TaskCard
import com.example.studyflow.ui.theme.Spacing
import com.example.studyflow.ui.theme.StudyFlowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    tasks: List<Task>,
    onTaskClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSubject by remember { mutableStateOf<String?>(null) }
    val visibleTasks = if (selectedSubject == null) tasks
    else tasks.filter { it.subject == selectedSubject }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("StudyFlow") },
                actions = {
                    TextButton(
                        onClick = onSeeAllClick,
                        modifier = Modifier.heightIn(min = Spacing.touchTarget)
                    ) { Text("All") }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+", style = MaterialTheme.typography.headlineMedium)
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = Spacing.md),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                items(sampleSubjects) { subject ->
                    SubjectChip(
                        label = subject,
                        selected = subject == selectedSubject,
                        onClick = {
                            selectedSubject = if (selectedSubject == subject) null else subject
                        }
                    )
                }
            }
            SectionHeader("Upcoming deadlines")
            if (visibleTasks.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No tasks yet",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(Spacing.md),
                    verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    items(visibleTasks, key = { it.id }) { task ->
                        TaskCard(task = task, onClick = { onTaskClick(task.id) })
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    StudyFlowTheme {
        HomeScreen(tasks = sampleTasks, onTaskClick = {}, onAddClick = {}, onSeeAllClick = {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeScreenDarkPreview() {
    StudyFlowTheme {
        HomeScreen(tasks = sampleTasks, onTaskClick = {}, onAddClick = {}, onSeeAllClick = {})
    }
}