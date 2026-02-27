package com.raulastete.lazypizza.presentation.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.presentation.model.RecommendedProductUi
import com.raulastete.lazypizza.ui.component.SecondaryIconButton
import com.raulastete.lazypizza.ui.theme.LazyPizzaTheme
import com.raulastete.lazypizza.ui.theme.body1Regular
import com.raulastete.lazypizza.ui.theme.title1Semibold

@Composable
fun RecommendedCard(
    recommendedProduct: RecommendedProductUi,
    modifier: Modifier = Modifier,
    onAddButtonClick: () -> Unit
) {
    Card(
        modifier = modifier
            .width(160.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
        ) {
            ImageContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                image = recommendedProduct.image
            )
            Details(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(82.dp)
                    .padding(12.dp),
                name = recommendedProduct.name,
                unitPrice = recommendedProduct.unitPrice,
                onAddButtonClick = onAddButtonClick
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
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
            )
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
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
private fun Details(
    modifier: Modifier,
    name: String,
    unitPrice: String,
    onAddButtonClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = name,
            maxLines = 1,
            style = MaterialTheme.typography.body1Regular.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = unitPrice,
                style = MaterialTheme.typography.title1Semibold
            )
            SecondaryIconButton(
                modifier = Modifier.size(22.dp),
                icon = ImageVector.vectorResource(R.drawable.plus_icon),
                tint = MaterialTheme.colorScheme.primary,
                onClick = onAddButtonClick
            )
        }
    }
}

@Composable
@Preview
private fun RecommendedCardPreview() {
    LazyPizzaTheme {
        RecommendedCard(
            recommendedProduct = RecommendedProductUi(
                id = "",
                image = "",
                name = "Margherita",
                unitPrice = "$8.00"
            ),
            onAddButtonClick = {}
        )
    }
}