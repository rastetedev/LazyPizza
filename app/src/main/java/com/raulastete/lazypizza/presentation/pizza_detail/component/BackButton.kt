package com.raulastete.lazypizza.presentation.pizza_detail.component

import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@Composable
fun BackButton(modifier: Modifier, onClick: () -> Unit) {
    FilledIconButton(
        modifier = modifier,
        onClick = onClick, colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = AppTheme.colorScheme.textSecondary8
        )
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left),
            contentDescription = null,
            tint = AppTheme.colorScheme.textSecondary
        )
    }
}