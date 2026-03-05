package com.raulastete.lazypizza.presentation.component

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.raulastete.lazypizza.presentation.theme.LazyPizzaTheme
import com.raulastete.lazypizza.presentation.theme.Primary8

@Composable
fun PrimaryIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Primary8,
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

@Preview
@Composable
private fun PrimaryIconButtonPreview() {
    LazyPizzaTheme {
        PrimaryIconButton(
            onClick = {},
            icon = {}
        )
    }
}