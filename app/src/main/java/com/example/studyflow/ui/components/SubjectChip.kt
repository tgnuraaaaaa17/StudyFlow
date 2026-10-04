package com.example.studyflow.ui.components

import android.content.res.Configuration
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.studyflow.ui.theme.StudyFlowTheme

@Composable
fun SubjectChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label, maxLines = 1, overflow = TextOverflow.Ellipsis) },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun SubjectChipPreview() {
    StudyFlowTheme { SubjectChip(label = "Math", selected = true, onClick = {}) }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SubjectChipDarkPreview() {
    StudyFlowTheme { SubjectChip(label = "Math", selected = false, onClick = {}) }
}