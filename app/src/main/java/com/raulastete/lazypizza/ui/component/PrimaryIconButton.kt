package com.raulastete.lazypizza.ui.component

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.raulastete.lazypizza.ui.theme.Primary8

@Composable
fun PrimaryIconButton(
    containerColor: Color = Primary8,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,

    icon: @Composable () -> Unit,
) {
    FilledIconButton(
        onClick = onClick,
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = containerColor
        ),
        modifier = modifier,
        shape = CircleShape
    ) {
        icon()
    }
}