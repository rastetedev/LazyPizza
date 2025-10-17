package com.raulastete.lazypizza.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun FadingEdgeVerticalList(
    modifier: Modifier = Modifier,
    listState: ScrollableState,
    fadeHeight: Dp = 40.dp,
    fadeColor: Color = Color.White,
    list: @Composable () -> Unit
) {

    val showTopFade by remember {
        derivedStateOf { listState.canScrollBackward }
    }

    val showBottomFade by remember {
        derivedStateOf { listState.canScrollForward }
    }

    Box(modifier = modifier) {
        list()

        AnimatedVisibility(
            visible = showTopFade,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(fadeHeight)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                fadeColor,
                                Color.Transparent
                            )
                        )
                    )
            )
        }

        AnimatedVisibility(
            visible = showBottomFade,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(fadeHeight)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                fadeColor
                            )
                        )
                    )
            )
        }
    }
}