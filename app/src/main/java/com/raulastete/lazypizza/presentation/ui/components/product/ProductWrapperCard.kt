package com.raulastete.lazypizza.presentation.ui.components.product

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
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage

@Composable
fun ProductWrapperCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
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
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        )
    ) {

        val imageContainerShape = RoundedCornerShape(
            topStart = 12.dp,
            bottomStart = 12.dp,
            topEnd = 0.dp,
            bottomEnd = 0.dp
        )

        Row(
            modifier = Modifier
                .padding(2.dp)
                .height(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .size(120.dp)
                    .clip(shape = imageContainerShape)
                    .background(color = AppTheme.colorScheme.surfaceHighest),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(model = imageUrl, contentDescription = null)
            }

            content()
        }
    }
}

@Composable
@Preview
fun GenericProductCardPreview() {
    AppTheme {
        ProductWrapperCard(
            modifier = Modifier.fillMaxWidth(),
            imageUrl = "",
            content = {
                Text(text = "Pizza")
            }
        )
    }
}