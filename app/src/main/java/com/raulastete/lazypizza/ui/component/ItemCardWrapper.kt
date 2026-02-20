package com.raulastete.lazypizza.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.raulastete.lazypizza.ui.theme.LazyPizzaTheme

@Composable
fun ItemCardWrapper(
    image: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Card(
        onClick = { onClick?.invoke() },
        modifier = modifier
            .height(120.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
        ) {
            ImageContainer(
                modifier = Modifier
                    .weight(0.3f),
                image = image
            )
            CustomContentContainer(
                modifier = Modifier
                    .weight(0.7f),
                content = content
            )
        }
    }
}

@Composable
private fun ImageContainer(
    modifier: Modifier,
    image: String
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
            )
            .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = image,
            contentDescription = null
        )
    }
}

@Composable
private fun CustomContentContainer(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        content()
    }
}

@Preview
@Composable
private fun ItemCardWrapperPreview() {
    LazyPizzaTheme {
        ItemCardWrapper(
            image = "ddsfdsf",
            modifier = Modifier.fillMaxWidth(),
            onClick = null,
            content = {
                Column() {
                    Text("Margherita")
                    Text("Tomato sauce, mozzarella, fresh basil, olive oil")
                    Text("$8.99")
                }
            }
        )
    }
}