# AI Usage

## Which AI tools did I use, and for what?
I used Claude only. I used it to:
- understand what the assignment requires and what I can skip (saving data, real add/edit/delete logic);
- plan the order of work (sketches, theme, data, components, screens, navigation);
- review my sketches (it told me what labels were missing or wrong);
- generate Compose code for the theme `Spacing` object, the components, the screens and navigation;
- help with Git problems and with build errors in Android Studio.

## My 3 most useful prompts
1.(with the SIS3 and StudyFlow PDFs attached)
"Негізі не істеу керек? Типа UI дизайнын істесем болды ма?"
This told me I only need the UI part of StudyFlow, with hardcoded data, and showed what is mandatory.

2."У меня сейчас нету бумаги, можно на сайт нарисовать? И дай примерные эскизы на мой проект."
I got a labeled example sketch of my 4 screens and a tool (Excalidraw) to draw my own.

3."Это как тебе?" (sent several times together with a screenshot of my sketch)
Each time I got a short review: what is correct and what must be fixed (for example, the "+" drawn in the top bar but labeled FAB, and 3 rows of chips labeled LazyRow).

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
