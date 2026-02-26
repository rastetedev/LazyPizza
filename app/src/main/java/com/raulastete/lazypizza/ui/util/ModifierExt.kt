package com.raulastete.lazypizza.ui.util

import androidx.compose.animation.core.animateFloatAsState
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
                    0.2f to color.copy(alpha = 0.4f), // Comienza a ganar opacidad rápido
                    0.5f to color.copy(alpha = 0.8f), // Ya es casi opaco a la mitad
                    1.0f to color,                    // Totalmente opaco al final
                    startY = size.height - heightPx,
                    endY = size.height
                ),
                alpha = alpha
            )
        }
    }
}


/**
 *
 * /**
 *  * Aplica un efecto de desvanecimiento (fading edge) en la parte inferior de un LazyGrid.
 *  * El efecto desaparece automáticamente cuando se llega al final de la lista.
 *  *
 *  * @param state El estado del LazyGrid para monitorear el scroll.
 *  * @param height La altura del degradado.
 *  * @param color El color base para el degradado (se utiliza su opacidad para el mask).
 *  */
 * fun Modifier.bottomFadingEdge(
 *     state: LazyGridState,
 *     height: Dp = 40.dp,
 *     color: Color = Color.Black
 * ): Modifier = this
 *     // Requerido para que BlendMode.DstIn funcione correctamente sobre el contenido
 *     .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
 *     .drawWithContent {
 *         drawContent()
 *
 *         // Solo dibujamos el degradado si podemos seguir haciendo scroll hacia abajo
 *         if (state.canScrollForward && height > 0.dp && size.height > 0f) {
 *             val heightPx = height.toPx()
 *
 *             // Calculamos en qué punto porcentual comienza el desvanecimiento
 *             val ratio = ((size.height - heightPx) / size.height).coerceIn(0f, 1f)
 *
 *             drawRect(
 *                 brush = Brush.verticalGradient(
 *                     0f to color,             // Opaco desde el inicio
 *                     ratio to color,          // Sigue opaco hasta que empieza el degradado
 *                     1f to Color.Transparent        // Transparente al final de la lista
 *                 ),
 *                 blendMode = BlendMode.DstIn
 *             )
 *         }
 *     }
 */