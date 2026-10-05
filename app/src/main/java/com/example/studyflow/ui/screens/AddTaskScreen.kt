package com.example.studyflow.ui.screens

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.studyflow.data.sampleSubjects
import com.example.studyflow.ui.components.SubjectChip
import com.example.studyflow.ui.theme.Spacing
import com.example.studyflow.ui.theme.StudyFlowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    subjects: List<String>,
    onBack: () -> Unit,
    onSave: (title: String, subject: String, deadline: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf<String?>(null) }
    val canSave = title.isNotBlank() && deadline.isNotBlank() && subject != null

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Add task") },
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
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Task") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = deadline,
                onValueChange = { deadline = it },
                label = { Text("Deadline (e.g. 10 Oct)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Text("Subject", style = MaterialTheme.typography.titleMedium)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                items(subjects) { s ->
                    SubjectChip(label = s, selected = s == subject, onClick = { subject = s })
                }
            }
            Button(
                onClick = { subject?.let { onSave(title, it, deadline) } },
                enabled = canSave,
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
    StudyFlowTheme { AddTaskScreen(sampleSubjects, onBack = {}, onSave = { _, _, _ -> }) }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AddTaskScreenDarkPreview() {
    StudyFlowTheme { AddTaskScreen(sampleSubjects, onBack = {}, onSave = { _, _, _ -> }) }
}