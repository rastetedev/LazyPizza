package com.raulastete.lazypizza.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.ui.theme.AppTheme
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height

@Composable
fun GenericProductCard(
    modifier: Modifier = Modifier,
    image: String,
    onClick: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit
) {
    Card(
        onClick = { onClick?.invoke() },
        enabled = onClick != null,
        modifier = modifier,
        shape = AppTheme.shape.card,
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colorScheme.surfaceHigher,
            disabledContainerColor = AppTheme.colorScheme.surfaceHigher
        )
    ) {
        Row(
            modifier = Modifier
                .padding(3.dp)
                .height(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .size(120.dp)
                    .background(
                        color = AppTheme.colorScheme.surfaceHighest,
                        shape = RoundedCornerShape(
                            topStart = 12.dp,
                            bottomStart = 12.dp,
                            topEnd = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 12.dp,
                            bottomStart = 12.dp,
                            topEnd = 0.dp,
                            bottomEnd = 0.dp
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                //TODO: Use Coil to load image URL
            }

            content()
        }
    }
}