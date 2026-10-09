package com.example.deadliner.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.deadliner.ui.theme.Spacing
import com.example.deadliner.ui.theme.DeadlinerTheme

@Composable
fun SectionHeader(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier.padding(horizontal = Spacing.md, vertical = Spacing.sm)
    )
}

@Preview(showBackground = true)
@Composable
private fun SectionHeaderPreview() {
    DeadlinerTheme { SectionHeader("Upcoming deadlines") }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SectionHeaderDarkPreview() {
    DeadlinerTheme { SectionHeader("Upcoming deadlines") }
}