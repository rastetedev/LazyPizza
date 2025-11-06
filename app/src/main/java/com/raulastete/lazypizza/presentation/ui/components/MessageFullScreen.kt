package com.raulastete.lazypizza.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.presentation.ui.components.designsystem.LPPrimaryButton
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@Composable
fun MessageFullScreen(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(120.dp))
        Text(title, style = AppTheme.typography.title1Medium)
        Text(
            subtitle,
            style = AppTheme.typography.body3Regular,
            color = AppTheme.colorScheme.textSecondary
        )
        Spacer(Modifier.height(20.dp))
        LPPrimaryButton(text = buttonText) {
            onClick()
        }
    }
}