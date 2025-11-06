package com.raulastete.lazypizza.presentation.ui.components.designsystem

import androidx.compose.material3.Badge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@Composable
fun LPBadge(
    modifier: Modifier = Modifier,
    cartCount: Int
) {
    Badge(
        modifier = modifier,
        containerColor = AppTheme.colorScheme.primary,
        contentColor = AppTheme.colorScheme.textOnPrimary
    ) {
        Text(cartCount.toString())
    }
}