package com.raulastete.lazypizza.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.ui.theme.AppTheme

@Composable
fun LPIconButton(
    @DrawableRes icon: Int,
    iconTint: Color = AppTheme.colorScheme.primary,
    onClick: () -> Unit,
) {
    OutlinedIconButton(
        modifier = Modifier.size(22.dp),
        onClick = onClick,
        border = BorderStroke(
            width = 1.dp,
            color = AppTheme.colorScheme.outline50,
        ),
        shape = AppTheme.shape.small
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(icon),
            contentDescription = null,
            modifier = Modifier.size(14.dp),
            tint = iconTint
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LPIconButtonPreview() {
    AppTheme {
        LPIconButton(
            icon = R.drawable.ic_trash
        ) { }
    }
}