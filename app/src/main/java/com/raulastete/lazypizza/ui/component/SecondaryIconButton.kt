package com.raulastete.lazypizza.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.ui.theme.Outline50

@Composable
fun SecondaryIconButton(
    icon: ImageVector,
    tint: Color,
    iconSize: Dp = 14.dp,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedIconButton(
        onClick = onClick,
        border = BorderStroke(
            width = 1.dp,
            color = Outline50
        ),
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Icon(
            modifier = Modifier.size(iconSize),
            imageVector = icon,
            tint = tint,
            contentDescription = null
        )
    }
}