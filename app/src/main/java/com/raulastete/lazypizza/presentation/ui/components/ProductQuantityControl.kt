package com.raulastete.lazypizza.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@Composable
fun ProductQuantityControl(
    modifier: Modifier = Modifier,
    count: Int,
    isIncreaseButtonEnabled: Boolean = true,
    onClickIncreaseCount: () -> Unit,
    onClickDecreaseCount: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LPIconButton(
            icon = R.drawable.ic_minus,
            iconTint = AppTheme.colorScheme.textSecondary,
            onClick = onClickDecreaseCount
        )

        Text(
            text = count.toString(),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .widthIn(min = 40.dp, max = 80.dp),
            style = AppTheme.typography.title2,
            maxLines = 1,
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