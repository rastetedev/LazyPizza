package com.raulastete.lazypizza.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@Composable
fun LPGhostButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(AppTheme.shape.button)
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = AppTheme.colorScheme.primary8)
            )
            .background(
                color = Color.Transparent,
                shape = AppTheme.shape.button,
            )
            .border(
                width = 1.dp,
                color =AppTheme.colorScheme.primary8,
                shape = AppTheme.shape.button
            )
            .padding(10.dp),
    ) {
        Text(
            text = text,
            autoSize = TextAutoSize.StepBased(
                minFontSize = 12.sp,
                maxFontSize = AppTheme.typography.title3.fontSize,
            ),
            style = AppTheme.typography.title3,
            color = AppTheme.colorScheme.primary,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LPPrimaryButtonPreview() {
    AppTheme {
        LPGhostButton(
            modifier = Modifier.wrapContentWidth(),
            text = "Add to Cart"
        ) {

        }
    }
}