package com.raulastete.lazypizza.presentation.menu.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.R

@Composable
fun Banner(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp)),
        painter = painterResource(R.drawable.banner),
        contentDescription = null,
        contentScale = ContentScale.FillWidth
    )
}