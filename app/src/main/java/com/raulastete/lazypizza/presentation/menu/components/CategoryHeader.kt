package com.raulastete.lazypizza.presentation.menu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@Composable
fun CategoryHeader(name: String) {
    Text(
        text = name,
        style = AppTheme.typography.label2Semibold,
        color = AppTheme.colorScheme.textSecondary,
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colorScheme.background)
            .padding(vertical = 12.dp)
            .padding(start = 16.dp)
    )
}