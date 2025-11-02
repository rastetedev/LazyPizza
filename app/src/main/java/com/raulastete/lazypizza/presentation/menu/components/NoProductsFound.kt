package com.raulastete.lazypizza.presentation.menu.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@Composable
fun NoProductsFound(
    modifier: Modifier = Modifier
) {
    Box(
        modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.no_results_message),
            style = AppTheme.typography.title3,
            color = AppTheme.colorScheme.textSecondary,
            textAlign = TextAlign.Center
        )
    }
}