package com.example.deadliner.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.deadliner.data.sampleSubjects
import com.example.deadliner.data.sampleTasks
import com.example.deadliner.ui.TasksUiState
import com.example.deadliner.ui.TasksViewModel
import com.example.deadliner.ui.components.SectionHeader
import com.example.deadliner.ui.components.SubjectChip
import com.example.deadliner.ui.components.TaskCard
import com.example.deadliner.ui.theme.DeadlinerTheme
import com.example.deadliner.ui.theme.Spacing

@Composable
fun HomeRoute(
    viewModel: TasksViewModel,
    onTaskClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    onSeeAllClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        uiState = uiState,
        onSearchChange = viewModel::onSearchChange,
        onSubjectSelected = viewModel::onSubjectSelected,
        onFavouriteClick = viewModel::onFavouriteClick,
        onRetry = viewModel::loadTasks,
        onTaskClick = onTaskClick,
        onAddClick = onAddClick,
        onSeeAllClick = onSeeAllClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: TasksUiState,
    onSearchChange: (String) -> Unit,
    onSubjectSelected: (String) -> Unit,
    onFavouriteClick: (Int) -> Unit,
    onRetry: () -> Unit,
    onTaskClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Deadliner") },
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
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = onSearchChange,
                label = { Text("Search tasks") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.md)
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = Spacing.md, vertical = Spacing.sm),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                items(uiState.subjects) { subject ->
                    SubjectChip(
                        label = subject,
                        selected = subject == uiState.selectedSubject,
                        onClick = { onSubjectSelected(subject) }
                    )
                }
            }
            SectionHeader("Upcoming deadlines")

            when {
                uiState.isLoading -> CenteredBox { CircularProgressIndicator() }

                uiState.error != null -> CenteredBox {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(Spacing.md)
                    ) {
                        Text(
                            text = uiState.error,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                        Button(
                            onClick = onRetry,
                            modifier = Modifier.heightIn(min = Spacing.touchTarget)
                        ) { Text("Retry") }
                    }
                }

                uiState.visibleTasks.isEmpty() -> CenteredBox {
                    Text(
                        text = "No tasks found",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                else -> LazyColumn(
                    contentPadding = PaddingValues(Spacing.md),
                    verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    items(uiState.visibleTasks, key = { it.id }) { task ->
                        TaskCard(
                            task = task,
                            onClick = { onTaskClick(task.id) },
                            onFavouriteClick = { onFavouriteClick(task.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CenteredBox(content: @Composable () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { content() }
}

private val contentState = TasksUiState(
    isLoading = false,
    allTasks = sampleTasks,
    visibleTasks = sampleTasks,
    subjects = sampleSubjects
)

@Composable
private fun PreviewHome(state: TasksUiState) {
    DeadlinerTheme {
        HomeScreen(
            uiState = state,
            onSearchChange = {}, onSubjectSelected = {}, onFavouriteClick = {},
            onRetry = {}, onTaskClick = {}, onAddClick = {}, onSeeAllClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Loading")
@Composable
private fun HomeLoadingPreview() = PreviewHome(TasksUiState(isLoading = true, subjects = sampleSubjects))

@Preview(showBackground = true, name = "Content")
@Composable
private fun HomeContentPreview() = PreviewHome(contentState)

@Preview(showBackground = true, name = "Content dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeContentDarkPreview() = PreviewHome(contentState)

@Preview(showBackground = true, name = "Empty")
@Composable
private fun HomeEmptyPreview() = PreviewHome(
    contentState.copy(visibleTasks = emptyList(), searchQuery = "zzz")
)

@Preview(showBackground = true, name = "Error")
@Composable
private fun HomeErrorPreview() = PreviewHome(
    TasksUiState(
        isLoading = false,
        error = "Could not load tasks. Check your connection.",
        subjects = sampleSubjects
    )
)