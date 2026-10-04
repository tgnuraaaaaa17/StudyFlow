package com.example.studyflow.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.studyflow.ui.theme.Spacing
import com.example.studyflow.ui.theme.StudyFlowTheme

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
    StudyFlowTheme { SectionHeader("Upcoming deadlines") }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SectionHeaderDarkPreview() {
    StudyFlowTheme { SectionHeader("Upcoming deadlines") }
}