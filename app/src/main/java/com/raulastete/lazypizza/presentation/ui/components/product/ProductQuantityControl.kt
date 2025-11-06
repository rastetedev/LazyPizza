package com.raulastete.lazypizza.presentation.ui.components.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.presentation.ui.components.designsystem.LPIconButton
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@Composable
fun ProductQuantityControl(
    modifier: Modifier = Modifier,
    count: Int,
    isIncreaseButtonEnabled: Boolean = true,
    isDecreaseButtonEnabled: Boolean = true,
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
            enabled = isDecreaseButtonEnabled,
            onClick = onClickDecreaseCount
        )

        Text(
            text = count.toString(),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .widthIn(min = 35.dp, max = 50.dp),
            style = AppTheme.typography.title2,
            maxLines = 1,
            autoSize = TextAutoSize.StepBased(
                minFontSize = 12.sp,
                maxFontSize = AppTheme.typography.title2.fontSize,
            ),
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