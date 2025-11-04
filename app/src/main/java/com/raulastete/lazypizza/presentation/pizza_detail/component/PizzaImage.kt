package com.raulastete.lazypizza.presentation.pizza_detail.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun PizzaImage(modifier: Modifier = Modifier, imageUrl: String?) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.FillHeight,
            alignment = Alignment.BottomCenter
        )
    }
}
