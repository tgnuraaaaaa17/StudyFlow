package com.example.deadliner.data

import kotlinx.coroutines.delay
import java.io.IOException

class TaskRepository(
    private var shouldFail: Boolean = false
) {
    private val tasks = sampleTasks.toMutableList()

    val subjects: List<String> = sampleSubjects

    suspend fun getTasks(): List<Task> {
        delay(1500)
        if (shouldFail) {
            shouldFail = false
            throw IOException("Could not load tasks. Check your connection.")
        }
        return tasks.toList()
    }

    fun addTask(title: String, subject: String, deadline: String): List<Task> {
        val newId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
        tasks.add(Task(newId, title, subject, deadline, "No description yet."))
        return tasks.toList()
    }

    fun toggleFavourite(id: Int): List<Task> {
        replace(id) { it.copy(isFavourite = !it.isFavourite) }
        return tasks.toList()
    }

    fun toggleCompleted(id: Int): List<Task> {
        replace(id) { it.copy(isCompleted = !it.isCompleted) }
        return tasks.toList()
    }

    private fun replace(id: Int, change: (Task) -> Task) {
        val index = tasks.indexOfFirst { it.id == id }
        if (index != -1) tasks[index] = change(tasks[index])
    }
}