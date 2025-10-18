package com.raulastete.lazypizza.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@Composable
fun LPBadge(count: String) {
    Box(
        Modifier
            .offset((8).dp, (-8).dp)
            .size(16.dp)
            .background(
                color = AppTheme.colorScheme.primary,
                CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            count,
            style = AppTheme.typography.title4,
            color = AppTheme.colorScheme.surfaceHigher
        )
    }
}