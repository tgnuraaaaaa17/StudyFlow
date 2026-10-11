Trace one click: user types "ess" in the search field
1. HomeScreen text field calls onSearchChange("ess")
2. That is viewModel::onSearchChange, passed by HomeRoute
3. TasksViewModel.onSearchChange runs _uiState.update { copy(searchQuery, visibleTasks = filter()) }
4. uiState emits new TasksUiState, HomeRoute collects it (collectAsStateWithLifecycle)
5. HomeScreen recomposes: text field shows "ess", list shows only matching tasks
