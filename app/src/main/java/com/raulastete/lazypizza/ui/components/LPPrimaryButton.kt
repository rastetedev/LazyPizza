package com.raulastete.lazypizza.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.ui.theme.AppTheme

@Composable
fun LPPrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clickable {
                onClick()
            }
            .background(
                brush = AppTheme.colorScheme.primaryGradient,
                shape = AppTheme.shape.button
            )
            .padding(
                vertical = 10.dp,
                horizontal = 24.dp
            ), contentAlignment = Alignment.Center
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
        ){

        }
    }
}