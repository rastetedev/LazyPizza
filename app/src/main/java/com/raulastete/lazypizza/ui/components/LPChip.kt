package com.raulastete.lazypizza.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.ui.theme.AppTheme

@Composable
fun LPChip(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    ElevatedAssistChip(
        modifier = modifier,
        onClick = onClick,
        label = {
            Text(
                text = text,
                style = AppTheme.typography.body3Medium,
                color = AppTheme.colorScheme.textPrimary
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = Color.Transparent,
        ),
        elevation = null,
        border = BorderStroke(width = 1.dp, color = AppTheme.colorScheme.outline)
    )
}

@Preview
@Composable
private fun LPChipPreview() {
    AppTheme {
        LPChip(
            text = "Example",
            onClick = {}
        )
    }
}