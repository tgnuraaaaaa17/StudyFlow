package com.example.studyflow.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.studyflow.data.Task
import com.example.studyflow.data.sampleSubjects
import com.example.studyflow.data.sampleTasks
import com.example.studyflow.ui.screens.AddTaskScreen
import com.example.studyflow.ui.screens.HomeScreen
import com.example.studyflow.ui.screens.TaskDetailScreen
import com.example.studyflow.ui.screens.TaskListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val tasks = remember { mutableStateListOf<Task>().apply { addAll(sampleTasks) } }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                tasks = tasks,
                onTaskClick = { id -> navController.navigate("detail/$id") },
                onAddClick = { navController.navigate("add") },
                onSeeAllClick = { navController.navigate("list") }
            )
        }
        composable("list") {
            TaskListScreen(
                tasks = tasks,
                onTaskClick = { id -> navController.navigate("detail/$id") },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = "detail/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { entry ->
            val taskId = entry.arguments?.getInt("taskId")
            val task = tasks.find { it.id == taskId }
            if (task != null) {
                TaskDetailScreen(task = task, onBack = { navController.popBackStack() })
            }
        }
        composable("add") {
            AddTaskScreen(
                subjects = sampleSubjects,
                onBack = { navController.popBackStack() },
                onSave = { title, subject, deadline ->
                    val newId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
                    tasks.add(Task(newId, title, subject, deadline, "No description yet."))
                    navController.popBackStack()
                }
            )
        }
    }
}