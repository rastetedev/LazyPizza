package com.raulastete.lazypizza.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@Composable
fun LPPrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = AppTheme.shape.button
            )
            .clip(AppTheme.shape.button)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
                onClick = onClick
            )
            .background(
                brush = AppTheme.colorScheme.primaryGradient,
                shape = AppTheme.shape.button
            )
            .padding(
                vertical = 10.dp,
                horizontal = 24.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = AppTheme.typography.title3,
            color = AppTheme.colorScheme.textOnPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LPPrimaryButtonPreview() {
    AppTheme {
        LPPrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Add to Cart for $12.99"
        ) {

        }
    }
}