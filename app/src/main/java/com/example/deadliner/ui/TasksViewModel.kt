package com.example.deadliner.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deadliner.data.Task
import com.example.deadliner.data.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

private const val TAG = "VM"

class TasksViewModel(
    private val repository: TaskRepository = TaskRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    init {
        Log.d(TAG, "TasksViewModel init")
        loadTasks()
    }

    override fun onCleared() {
        Log.d(TAG, "TasksViewModel onCleared")
        super.onCleared()
    }

    fun loadTasks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val tasks = repository.getTasks()
                _uiState.update { it.copy(subjects = repository.subjects) }
                publish(tasks)
            } catch (e: IOException) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message ?: "Something went wrong")
                }
            }
        }
    }

    fun onSearchChange(query: String) {
        _uiState.update {
            it.copy(
                searchQuery = query,
                visibleTasks = filter(it.allTasks, query, it.selectedSubject)
            )
        }
    }

    fun onSubjectSelected(subject: String) {
        _uiState.update {
            val newSubject = if (it.selectedSubject == subject) null else subject
            it.copy(
                selectedSubject = newSubject,
                visibleTasks = filter(it.allTasks, it.searchQuery, newSubject)
            )
        }
    }

    fun onFavouriteClick(id: Int) = publish(repository.toggleFavourite(id))

    fun onToggleCompleted(id: Int) = publish(repository.toggleCompleted(id))

    fun onDraftTitleChange(title: String) =
        _uiState.update { it.copy(draft = it.draft.copy(title = title)) }

    fun onDraftDeadlineChange(deadline: String) =
        _uiState.update { it.copy(draft = it.draft.copy(deadline = deadline)) }

    fun onDraftSubjectSelected(subject: String) =
        _uiState.update { it.copy(draft = it.draft.copy(subject = subject)) }

    fun onSaveTask() {
        val draft = _uiState.value.draft
        val subject = draft.subject ?: return
        if (!draft.canSave) return
        publish(repository.addTask(draft.title.trim(), subject, draft.deadline.trim()))
        _uiState.update { it.copy(draft = TaskDraft()) }
    }

    private fun publish(all: List<Task>) {
        _uiState.update {
            it.copy(
                isLoading = false,
                error = null,
                allTasks = all,
                visibleTasks = filter(all, it.searchQuery, it.selectedSubject)
            )
        }
    }

    private fun filter(tasks: List<Task>, query: String, subject: String?): List<Task> =
        tasks.filter { task ->
            (subject == null || task.subject == subject) &&
                    (query.isBlank() || task.title.contains(query.trim(), ignoreCase = true))
        }
}