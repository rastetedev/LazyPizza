package com.raulastete.lazypizza.presentation.pizza_detail

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.raulastete.lazypizza.ui.component.Counter
import com.raulastete.lazypizza.ui.theme.Outline
import com.raulastete.lazypizza.ui.theme.Primary8
import com.raulastete.lazypizza.ui.theme.body3Regular
import com.raulastete.lazypizza.ui.theme.title2

data class TopicDetails(
    val image: String,
    val name: String,
    val unitPrice: String,
    val count: Int = 0
)

@Composable
fun TopicCard(
    details: TopicDetails,
    onClick: () -> Unit,
    onClickDecreaseCountButton: () -> Unit,
    onClickIncreaseCountButton: () -> Unit
) {

    val strokeColor: Color by animateColorAsState(
        targetValue = if (details.count > 0) MaterialTheme.colorScheme.primary else Outline
    )
    Card(
        onClick = onClick,
        modifier = Modifier
            .height(142.dp)
            .width(124.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = 1.dp, color = strokeColor),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            ImageContainer(details.image)
            Details(
                name = details.name,
                unitPrice = details.unitPrice,
                count = details.count,
                onClickDecreaseCountButton = onClickDecreaseCountButton,
                onClickIncreaseCountButton = onClickIncreaseCountButton
            )
        }
    }
}

@Composable
private fun ImageContainer(
    image: String
) {
    Box(
        Modifier.height(80.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(
                    color = Primary8,
                    shape = RoundedCornerShape(percent = 50)
                ),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = image,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun Details(
    name: String,
    unitPrice: String,
    count: Int,
    onClickDecreaseCountButton: () -> Unit,
    onClickIncreaseCountButton: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = name,
            textAlign = TextAlign.Center,
            maxLines = 1,
            style = MaterialTheme.typography.body3Regular.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        AnimatedContent(count > 0) { hasCount ->
            if (hasCount) {
                Counter(
                    count = count,
                    onClickDecreaseCountButton = onClickDecreaseCountButton,
                    onClickIncreaseCountButton = onClickIncreaseCountButton
                )
            } else {
                Text(
                    text = unitPrice,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.title2
                )
            }
        }
    }
}