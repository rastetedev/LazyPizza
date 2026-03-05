package com.raulastete.lazypizza.presentation.util

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.bottomFadingEdge(
    state: LazyGridState,
    height: Dp = 40.dp,
    color: Color = Color.White
) = composed {

    val canScrollForward by remember {
        derivedStateOf { state.canScrollForward }
    }

    val alpha by animateFloatAsState(
        targetValue = if (canScrollForward) 1f else 0f,
        label = "FadingEdgeAnimation"
    )

    this.drawWithContent {
        drawContent()

        if (alpha > 0f) {
            val heightPx = height.toPx()
            drawRect(
                brush = Brush.verticalGradient(
                    0.0f to Color.Transparent,
                    0.2f to color.copy(alpha = 0.4f),
                    0.5f to color.copy(alpha = 0.8f),
                    1.0f to color,
                    startY = size.height - heightPx,
                    endY = size.height
                ),
                alpha = alpha
            )
        }
    }
}

fun Modifier.bottomFadingEdge(
    state: LazyListState,
    height: Dp = 40.dp,
    color: Color = Color.White
) = composed {

    val canScrollForward by remember {
        derivedStateOf { state.canScrollForward }
    }

    val alpha by animateFloatAsState(
        targetValue = if (canScrollForward) 1f else 0f,
        label = "FadingEdgeAnimation"
    )

    this.drawWithContent {
        drawContent()

        if (alpha > 0f) {
            val heightPx = height.toPx()
            drawRect(
                brush = Brush.verticalGradient(
                    0.0f to Color.Transparent,
                    0.2f to color.copy(alpha = 0.4f),
                    0.5f to color.copy(alpha = 0.8f),
                    1.0f to color,
                    startY = size.height - heightPx,
                    endY = size.height
                ),
                alpha = alpha
            )
        }
    }
}
