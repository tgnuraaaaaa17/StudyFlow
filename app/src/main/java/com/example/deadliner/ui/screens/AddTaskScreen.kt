package com.example.deadliner.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.deadliner.data.sampleSubjects
import com.example.deadliner.ui.TaskDraft
import com.example.deadliner.ui.TasksViewModel
import com.example.deadliner.ui.components.SubjectChip
import com.example.deadliner.ui.theme.DeadlinerTheme
import com.example.deadliner.ui.theme.Spacing

@Composable
fun AddTaskRoute(
    viewModel: TasksViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    AddTaskScreen(
        subjects = uiState.subjects,
        draft = uiState.draft,
        onTitleChange = viewModel::onDraftTitleChange,
        onDeadlineChange = viewModel::onDraftDeadlineChange,
        onSubjectSelected = viewModel::onDraftSubjectSelected,
        onSave = {
            viewModel.onSaveTask()
            onBack()
        },
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    subjects: List<String>,
    draft: TaskDraft,
    onTitleChange: (String) -> Unit,
    onDeadlineChange: (String) -> Unit,
    onSubjectSelected: (String) -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Add task") },
                navigationIcon = { IconButton(onClick = onBack) { Text("←") } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            OutlinedTextField(
                value = draft.title,
                onValueChange = onTitleChange,
                label = { Text("Task") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = draft.deadline,
                onValueChange = onDeadlineChange,
                label = { Text("Deadline (e.g. 10 Oct)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Text("Subject", style = MaterialTheme.typography.titleMedium)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                items(subjects) { s ->
                    SubjectChip(
                        label = s,
                        selected = s == draft.subject,
                        onClick = { onSubjectSelected(s) }
                    )
                }
            }
            Button(
                onClick = onSave,
                enabled = draft.canSave,
                modifier = Modifier.fillMaxWidth().heightIn(min = Spacing.touchTarget)
            ) {
                Text("Save")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddTaskScreenPreview() {
    DeadlinerTheme {
        AddTaskScreen(sampleSubjects, TaskDraft(), {}, {}, {}, {}, {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AddTaskScreenDarkPreview() {
    DeadlinerTheme {
        AddTaskScreen(sampleSubjects, TaskDraft("Essay", "10 Oct", "Math"), {}, {}, {}, {}, {})
    }
}