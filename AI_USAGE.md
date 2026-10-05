# AI Usage

## Which AI tools did I use, and for what?
I used Claude only. I used it to:
- understand what the assignment requires and what I can skip (saving data, real add/edit/delete logic);
- plan the order of work (sketches, theme, data, components, screens, navigation);
- review my sketches (it told me what labels were missing or wrong);
- generate Compose code for the theme `Spacing` object, the components, the screens and navigation;
- help with Git problems and with build errors in Android Studio.

## My 3 most useful prompts
1. (with a screenshot of the running Home screen) "но не нажимается"
   The task cards did nothing when tapped. The answer explained that `onTaskClick = {}` was an empty
   placeholder and gave me Navigation Compose, the `TaskDetailScreen`, and the route `detail/{taskId}`.
   This is how the task `id` reaches the detail screen as a navigation argument, and the screen finds
   the task with `tasks.find { it.id == taskId }`.
2. "+ плюс не работает не реагирует"
   The "+" button was also an empty placeholder because the Add Task screen did not exist yet. I got
   `AddTaskScreen` (two text fields, subject chips, a Save button that is enabled only when the form is
   filled) and a task list in `AppNavigation` that survives navigation, so a new task appears on Home
   after Save. The form uses `remember` + `mutableStateOf`.
3. "почему когда я нажимаю алл сразу входить из приложение"
   The app crashed when I pressed "All" on the Home screen. The cause was a missing `"list"` route in
   `NavHost`: navigation could not find the destination. I got the complete `AppNavigation` with all
   four routes (`home`, `list`, `detail/{taskId}`, `add`) and the `TaskListScreen`.
   
## One case where the AI was wrong
The AI gave me `TaskCard` code that used `Icons.Filled.CheckCircle`, but it did not say that the
material-icons library has to be added to the project. The build failed with
"Unresolved reference 'Icons'" (5 errors). I found it in the Build Output tab, and after the first
suggested fix (adding a dependency in `build.gradle.kts`) still failed, I replaced the icon with a
`Checkbox`, which is part of Material3 and needs no extra library. The build then succeeded and the
previews worked.

Other small mistakes by the AI that I noticed:
- It gave me terminal commands like `git commit --allow-empty -m ""` that were not needed and only
  produced errors; I ignored them.
- It gave me code for `HomeScreen` as small pieces to change, and when I applied them by hand, the
  file broke (a parameter ended up inside the function, braces were wrong). I replaced the whole file
  with a complete version and it worked.

## What I changed or wrote by hand
- Drew all 4 sketches myself in Excalidraw, labeled the blocks, and fixed them after feedback
  (labels, typos, the FAB, one row of chips). Exported them as PNG and made the first commit.
- Created the packages and folders (`data`, `ui/components`, `ui/screens`), moved `components` out of `theme`
  when the package name did not match the folder.
- Added the theme files from Material Theme Builder and the `Spacing` object, and checked the
  package lines and the theme name.
- Fixed imports, added the Navigation dependency in `build.gradle.kts` and synced Gradle.
- Solved the rejected `git push` (the remote already had a commit) and updated the remote URL after
  the repository moved.
- Tested the app on the emulator, turned on dark mode, took all screenshots and renamed them.
- Wrote the final `README.md` and this file for my repository.
