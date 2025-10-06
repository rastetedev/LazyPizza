package com.raulastete.lazypizza.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.ui.theme.AppTheme

@Composable
fun ProductQuantityControl(
    modifier: Modifier = Modifier,
    count: Int,
    isIncreaseButtonEnabled: Boolean = true,
    onClickIncreaseCount: () -> Unit,
    onClickDecreaseCount: () -> Unit,
) {
    Row(modifier = modifier) {
        LPIconButton(
            icon = R.drawable.ic_minus,
            iconTint = AppTheme.colorScheme.textSecondary,
            onClick = onClickDecreaseCount
        )

        Text(
            text = count.toString(),
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
            style = AppTheme.typography.title2,
            color = AppTheme.colorScheme.textPrimary
        )

        LPIconButton(
            icon = R.drawable.ic_plus,
            iconTint = AppTheme.colorScheme.textSecondary,
            enabled = isIncreaseButtonEnabled,
            onClick = onClickIncreaseCount
        )
    }
}