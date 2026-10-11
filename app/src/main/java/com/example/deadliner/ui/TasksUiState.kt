package com.example.deadliner.ui

import com.example.deadliner.data.Task

data class TaskDraft(
    val title: String = "",
    val deadline: String = "",
    val subject: String? = null
) {
    val canSave: Boolean
        get() = title.isNotBlank() && deadline.isNotBlank() && subject != null
}

data class TasksUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val allTasks: List<Task> = emptyList(),
    val visibleTasks: List<Task> = emptyList(),
    val subjects: List<String> = emptyList(),
    val searchQuery: String = "",
    val selectedSubject: String? = null,
    val draft: TaskDraft = TaskDraft()
)
)
)