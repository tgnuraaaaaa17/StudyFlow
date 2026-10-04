package com.example.studyflow.data

data class Task(
    val id: Int,
    val title: String,
    val subject: String,
    val deadline: String,
    val description: String,
    val isCompleted: Boolean = false
)

val sampleSubjects = listOf("Math", "History", "ML", "CSS", "Linux", "API", "Kazakh language")

val sampleTasks = listOf(
    Task(1, "Essay: Climate change", "History", "10 Oct", "Write a 3-page essay about the causes and effects of climate change."),
    Task(2, "Linear algebra homework", "Math", "10 Oct", "Solve problems 1-15 from chapter 4."),
    Task(3, "Train a classifier", "ML", "12 Oct", "Train a simple classifier on the Iris dataset and report accuracy."),
    Task(4, "Responsive landing page", "CSS", "14 Oct", "Build a landing page with flexbox and a media query."),
    Task(5, "Bash scripting lab", "Linux", "15 Oct", "Write a script that backs up a folder every day."),
    Task(6, "REST API design", "API", "17 Oct", "Design endpoints for a library system and document them."),
    Task(7, "Kazakh dictation", "Kazakh language", "18 Oct", "Prepare for the dictation on chapter 5."),
    Task(8, "Calculus midterm prep", "Math", "20 Oct", "Review limits, derivatives and integrals."),
    Task(9, "WW2 presentation", "History", "22 Oct", "Prepare a 10-slide presentation about the causes of World War 2."),
    Task(10, "Neural network report", "ML", "24 Oct", "Write a short report comparing two network architectures."),
    Task(11, "Flexbox practice", "CSS", "26 Oct", "Complete 10 flexbox exercises."),
    Task(12, "Final project proposal with a really long title that must be cut with an ellipsis", "API", "30 Oct", "Describe your final project idea in one page.")
)