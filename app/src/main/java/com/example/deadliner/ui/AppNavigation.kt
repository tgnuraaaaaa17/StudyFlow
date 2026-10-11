package com.example.deadliner.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.deadliner.ui.screens.AddTaskRoute
import com.example.deadliner.ui.screens.HomeRoute
import com.example.deadliner.ui.screens.TaskDetailRoute
import com.example.deadliner.ui.screens.TaskListRoute

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: TasksViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeRoute(
                viewModel = viewModel,
                onTaskClick = { id -> navController.navigate("detail/$id") },
                onAddClick = { navController.navigate("add") },
                onSeeAllClick = { navController.navigate("list") }
            )
        }
        composable("list") {
            TaskListRoute(
                viewModel = viewModel,
                onTaskClick = { id -> navController.navigate("detail/$id") },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = "detail/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { entry ->
            TaskDetailRoute(
                taskId = entry.arguments?.getInt("taskId"),
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable("add") {
            AddTaskRoute(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}